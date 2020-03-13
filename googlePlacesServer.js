const fs = require("fs");
const csv = require("fast-csv");
const axios = require("axios");
const readline = require("readline");
const { google } = require("googleapis");

let records = [];

const SCOPES = ["https://www.googleapis.com/auth/spreadsheets.readonly"];

// The file token.json stores the user's access and refresh tokens, and is
// created automatically when the authorization flow completes for the first
// time.
const TOKEN_PATH = "token.json";
const CREDENTIALS_PATH = "credentials.json";

// Load client secrets from a local file.
fs.readFile(CREDENTIALS_PATH, (err, content) => {
  if (err) return console.log("Error loading client secret file:", err);
  // Authorize a client with credentials, then call the Google Sheets API.
  authorize(JSON.parse(content), listMajors);
});

function authorize(credentials, callback) {
  const { client_secret, client_id, redirect_uris } = credentials.installed;
  const oAuth2Client = new google.auth.OAuth2(
    client_id,
    client_secret,
    redirect_uris[0]
  );

  // Check if we have previously stored a token.
  fs.readFile(TOKEN_PATH, (err, token) => {
    if (err) return getNewToken(oAuth2Client, callback);
    oAuth2Client.setCredentials(JSON.parse(token));
    callback(oAuth2Client);
  });
}

function getNewToken(oAuth2Client, callback) {
  const authUrl = oAuth2Client.generateAuthUrl({
    access_type: "offline",
    scope: SCOPES
  });
  console.log("Authorize this app by visiting this url:", authUrl);
  const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
  });
  rl.question("Enter the code from that page here: ", code => {
    rl.close();
    oAuth2Client.getToken(code, (err, token) => {
      if (err)
        return console.error(
          "Error while trying to retrieve access token",
          err
        );
      oAuth2Client.setCredentials(token);
      // Store the token to disk for later program executions
      fs.writeFile(TOKEN_PATH, JSON.stringify(token), err => {
        if (err) return console.error(err);
        console.log("Token stored to", TOKEN_PATH);
      });
      callback(oAuth2Client);
    });
  });
}

function listMajors(auth) {
  const sheets = google.sheets({ version: "v4", auth });
  console.log("sheets.spreadsheets", sheets.spreadsheets);
  sheets.spreadsheets.values.get(
    {
      spreadsheetId: "1-QcM_kvlIjAgMkun7DEDybliatGO1VO3UdY6X0oX7kg",
      range: "A1:80",
      majorDimension: "ROWS"
    },
    (err, res) => {
      if (err) return console.log("The API returned an error: " + err);
      const rows = res.data.values;
      if (rows.length) {
        console.log("Name, Major:");
        // Print columns A and E, which correspond to indices 0 and 4.
        // rows.map(row => {
        //   console.log(
        //     `${row[0]},${row[1]}, ${row[2]}, ${row[3]},  ${row[4]}, ${row[5]}, ${row[6]}, ${row[7]}, ${row[8]}, ${row[9]}`
        //   );
        // });

        return Promise.all(
          rows.map((row, ind) => {
            if (ind > 0) {
              return requestGooglePlacesData(row[1]);
            }
          })
        )
          .then(data => {
            records = data.map(i => {
              if (
                i !== undefined &&
                i.result !== undefined &&
                i.result !== null
              ) {
                return {
                  name: i.result ? i.result.name : "null",
                  google_places_id: i.result.place_id,
                  type: "",
                  city: "helsinki",
                  address: i.result.formatted_address,
                  coordinates: {
                    geometry: i.result.geometry
                  }
                };
              } else {
                return {
                  name: "null",
                  google_places_id: "unknown id",
                  type: "",
                  city: "helsinki",
                  address: "",
                  coordinates: {
                    geometry: ""
                  }
                };
              }
            });

            console.log("name", records);
            csvWriter
              .writeRecords(records) // returns a promise
              .then(() => {
                console.log("...Done");
              });

            // const resource = {
            //   properties: {
            //     title
            //   }
            // };

            // https://developers.google.com/sheets/api/reference/rest/v4/spreadsheets/create

          //   sheets.spreadsheets.create(
          //     {
          //       resource,
          //       fields: "spreadsheetId"
          //     },
          //     (err, spreadsheet) => {
          //       if (err) {
          //         // Handle error.
          //         console.log(err);
          //       } else {
          //         console.log(`Spreadsheet ID: ${spreadsheet.spreadsheetId}`);
          //       }
          //     }
          //   );
          // })
          // .catch(e => console.log(e));
      } else {
        console.log("No data found.");
      }
    }
  );
}

const createCsvWriter = require("csv-writer").createObjectCsvWriter;

const csvWriter = createCsvWriter({
  path: "./placesUpdated.csv",
  header: [
    { id: "name", title: "Name" },
    { id: "google_places_id", title: "Google Places ID" },
    { id: "type", title: "Type" },
    { id: "city", title: "City" },
    { id: "address", title: "Address" },
    { id: "coordinates", title: "Coordinates" }
  ]
});

async function readFromFile() {
  let infoArray = [];
  return await new Promise((resolve, reject) => {
    fs.createReadStream("places.csv")
      .pipe(csv.parse())
      .on("data", row => {
        if (row[1]) {
          infoArray.push(row);
        } else {
          infoArray.push("null");
        }
      })
      .on("error", error => {
        console.error(error);
      })
      .on("end", rowCount => {
        console.log(`Parsed ${rowCount} rows`);
        // console.log(`created array ${infoArray}`);
        return resolve(infoArray);
      });
  });
}

async function requestGooglePlacesData(placeID) {
  return new Promise((resolve, reject) => {
    return axios
      .get(
        `https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyDEx5OVeNl-xCLjux2i5sAENT6c6WW5stM&place_id=${placeID}`
      )
      .then(function({ data }) {
        resolve(data);
      })
      .catch(function(error) {
        console.log(error);
        reject("error");
      });
  });
}

// readFromFile()
// .then(data => {
//   return Promise.all(
//     data.map((item, ind) => {
//       if (ind > 0) {
//         return requestGooglePlacesData(item[1]);
//       }
//     })
//   );
// })
// .then(data => {
//   records = data.map(i => {
//     if (i !== undefined && i.result !== undefined && i.result !== null) {
//       return {
//         name: i.result ? i.result.name : "null",
//         google_places_id: i.result.place_id,
//         type: "",
//         city: "helsinki",
//         address: i.result.formatted_address,
//         coordinates: {
//           geometry: i.result.geometry
//         }
//       };
//     } else {
//       return {
//         name: "null",
//         google_places_id: "unknown id",
//         type: "",
//         city: "helsinki",
//         address: "",
//         coordinates: {
//           geometry: ""
//         }
//       };
//     }
//   });

//   console.log("name", records);
//   csvWriter
//     .writeRecords(records) // returns a promise
//     .then(() => {
//       console.log("...Done");
//     });
// })
// .catch(e => console.log(e));

// for one place, request

// https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyDEx5OVeNl-xCLjux2i5sAENT6c6WW5stM&place_id=ChIJdS56ErYLkkYRT1L1SZqc4tU

// Make a request for a user with a given ID
