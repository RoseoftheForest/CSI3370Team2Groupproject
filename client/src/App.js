import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Container } from "reactstrap";

import Header from "./components/Header";
import Footer from "./components/Footer";
import Error from "./components/Error"
import Loading from "./components/Loading"
import Home from "./views/pages/Home";
import Profile from "./views/pages/Profile";
import Game from "./views/pages/Game";
import { useAuth0 } from "@auth0/auth0-react";
import history from "./utils/history";

import "./App.css";

import initFontAwesome from "./utils/initFontAwesome";
initFontAwesome();

const App = () => {
  const { isLoading, error } = useAuth0();

  if (error) {
    return <Error />;
  }

  if (isLoading) {
    return <Loading />;
  }

  return (
    <BrowserRouter history={history}>
      <div id="app" className="d-flex flex-column h-100">
        <Header />
        <Container className="flex-grow-1 mt-5">
          <Routes>
            <Route path="/" exact component={Home} />
            <Route path="/profile" component={Profile} />
            <Route path="/game" component={Game} />
          </Routes>
        </Container>
        <Footer />
      </div>
    </BrowserRouter>
  );
};

export default App;