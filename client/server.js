const express = require("express");
const helmet = require("helmet");
const { join } = require("path");

require('dotenv').config();

const app = express();

const port = process.env.SERVER_PORT || 3000;

app.use(
    helmet({
        contentSecurityPolicy: false
    })
);

app.use(express.static(join(__dirname, "build")));

app.get('*', (req, res) => res.sendFile(join(__dirname, 'build', 'index.html')));

app.listen(port, () => console.log(`Server listening on port ${port}`));