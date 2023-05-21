/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */
const functions = require('firebase-functions');
const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");
const admin = require('firebase-admin');
var serviceAccount = require("./zbesp-a6692-firebase-adminsdk-qmss8-52a16ec269.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://zbesp-a6692-default-rtdb.europe-west1.firebasedatabase.app"
});

exports.sendNotificationsComments = functions.firestore.document('/comments/{commentId}').onWrite(async (change, context) => {
        const tokensSnapshot = await admin.firestore().collection('tokens').get();
        const tokens = tokensSnapshot.docs.map((doc) => {
          console.log(doc.id);
          return doc.id;
        });


        const payload = {
            notification: {
              title: 'New Comment',
              body: 'There is a new post related to a LEZ',
            },
          };

          try {
            await admin.messaging().sendToDevice(tokens, payload);
            console.log('Notification sent successfully.');
          } catch (error) {
            console.error('Error sending notification:', error);
          }
    });

exports.sendNotificationsVehicle = functions.firestore.document('/vehicles/{vehiclesId}').onWrite(async (change, context) => {
        const tokensSnapshot = await admin.firestore().collection('tokens').get();
        const tokens = tokensSnapshot.docs.map((doc) => {
          console.log(doc.id);
          return doc.id;
        });


        const payload = {
            notification: {
              title: 'New Vehicle',
              body: 'There is a new vehicle in your community',
            },
          };

          try {
            await admin.messaging().sendToDevice(tokens, payload);
            console.log('Notification sent successfully.');
          } catch (error) {
            console.error('Error sending notification:', error);
          }
    });
//exports.sendNotifications = functions.firestore.document('/comments/{commentId}').onWrite((change, context) => {
//  var payload = {
//    notification: {
//      title: `New comment`,
//      body: 'A user has added a new comment',
//    }
//  };
//      const getDeviceTokensPromise = admin.firestore().collection('tokens').get;
//      return Promise.all([getDeviceTokensPromise]).then(results => {
//          let tokensSnapshot;
//          let tokens;
//          tokensSnapshot = results[0];
//          console.log('tokensReg', "tokens");
//          tokens = Object.keys(tokensSnapshot.val());
//          return admin.messaging().sendToDevice(tokens, payload);
//    });
//  });
