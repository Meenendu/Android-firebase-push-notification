const app = require("express")();
const bodyParser = require("body-parser");

app.use(bodyParser.json());

var admin = require("firebase-admin");

var serviceAccount = require("./firebase-adminsdk.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://carnival-b6e41.firebaseio.com"
});

function sendMessage(req, res) {
  console.log(req.body, req.params.token);
  const payload = {
    notification: {
      title: "FCM Testing",
      body: "New Message Received"
    },
    data: {
      message_title: req.body.title,
      message_body: req.body.body
    }
  };

  const options = {
    priority: "high",
    timeToLive: 60 * 60 * 24
  };

  admin
    .messaging()
    .sendToDevice(req.params.token, payload, options)
    .then(function(response) {
      res.send(response);
      console.log("Successfully sent message:", response);
    })
    .catch(function(error) {
      res.send(error);
      console.log("Error sending message:", error);
    });

  // send msg to subscribed users

  // var topic = "weather";

  // var message = {
  //   notification: {
  //     title: "FCM using node server",
  //     body: "Burrraahhhh its working!!!!"
  //   },
  //   data: {
  //     score: "850",
  //     time: "2:45"
  //   },
  //   topic: topic
  // };
  // // Send a message to devices subscribed to the provided topic.
  // admin
  //   .messaging()
  //   .send(message)
  //   .then(response => {
  //     res.send(response);
  //     // Response is a message ID string.
  //     console.log("Successfully sent message:", response);
  //   })
  //   .catch(error => {
  //     res.send(error);
  //     console.log("Error sending message:", error);
  //   });
}

function sendMessage2(req, res) {
  console.log(req.body);
  const token =
    "d6mJp0LvODc:APA91bEE5xUh_Rl1LVL6O01pCwVIFAVu2qvrYcxjfYt9vLOR8XLrYTAlXlXQaGiopCnIWywfIaAQ1TSqZexYeXUIqDHlDD31tDI_K_vSmfP-LgfyYyZW7c-bAceoKKAjB96xHUZP_0GZ";

  const payload = {
    notification: {
      title: "FCM Testing",
      body: "New Message Received"
    },
    data: {
      message_title: "Message send through Node server",
      message_body: "Hey its working!!!!"
    }
  };

  const options = {
    priority: "high",
    timeToLive: 60 * 60 * 24
  };

  admin
    .messaging()
    .sendToDevice(token, payload, options)
    .then(function(response) {
      res.send(response);
      console.log("Successfully sent message:", response);
    })
    .catch(function(error) {
      res.send(error);
      console.log("Error sending message:", error);
    });
}

app.post("/send/:token", sendMessage);

app.get("/send", sendMessage2);

app.listen(3000, () => {
  console.log("Server is up at 3000");
});
