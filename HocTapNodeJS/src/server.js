import express from "express";
import bodyParser from "body-parser";
import viewEngine from "./config/viewEngine.js";
import initWebRoutes from "./route/web.js";
import dotenv from "dotenv";
import connectDB from "./config/connectDB.js";
import cors from 'cors';

require('dotenv').config();

let app = express();
app.use(cors({ credentials: true, origin: true }));
// Config app
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

viewEngine(app);
initWebRoutes(app);

connectDB();

const port = process.env.PORT || 6969;
app.listen(port, () => {
    console.log(`Backend Node.js is running on the port: ${port}`);
});
