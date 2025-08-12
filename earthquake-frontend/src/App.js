import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import UploadPage from './components/Upload';
import InspectPage from './components/Inspect';
import './App.css';

function App() {
    return (
        <Router>
            <div className="App">
                <nav className="navbar">
                    <div className="nav-container">
                        <h1 className="nav-title">Earthquake Data Manager</h1>
                        <div className="nav-links">
                            <Link to="/" className="nav-link">Upload Data</Link>
                            <Link to="/inspect" className="nav-link">Inspect Data</Link>
                        </div>
                    </div>
                </nav>
                
                <main className="main-content">
                    <Routes>
                        <Route path="/" element={<UploadPage />} />
                        <Route path="/inspect" element={<InspectPage />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
}

export default App;