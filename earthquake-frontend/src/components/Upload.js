import React, { useState } from 'react';
import axios from 'axios';

const UploadPage = () => {
    const [file, setFile] = useState(null);
    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState('');
    const [dragOver, setDragOver] = useState(false);

    const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

    const handleFileSelect = (event) => {
        const selectedFile = event.target.files[0];
        if (selectedFile) {
            setFile(selectedFile);
            setMessage('');
        } else {
            setMessage('Please select a valid CSV file.');
            setMessageType('error');
            setFile(null);
        }
    };

    const handleDrop = (event) => {
        event.preventDefault();
        setDragOver(false);
        
        const droppedFile = event.dataTransfer.files[0];
        if (droppedFile) {
            setFile(droppedFile);
            setMessage('');
        } else {
            setMessage('Please drop a valid CSV file.');
            setMessageType('error');
            setFile(null);
        }
    };

    const handleDragOver = (event) => {
        event.preventDefault();
        setDragOver(true);
    };

    const handleDragLeave = (event) => {
        event.preventDefault();
        setDragOver(false);
    };

    const handleUpload = async () => {
        if (!file) {
            setMessage('Please select a CSV file first.');
            setMessageType('error');
            return;
        }

        setUploading(true);
        setMessage('');

        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axios.post(`${API_BASE_URL}/csv/import`, formData, {headers: {'Content-Type': 'multipart/form-data'},});

            setMessage('CSV file uploaded successfully!');
            setMessageType('success');
            setFile(null);
            
            // Reset file input
            const fileInput = document.getElementById('csvFile');
            if (fileInput) fileInput.value = '';
            
        } catch (error) {
            console.error('Upload error:', error);
            setMessage(error.response?.data?.message || 'Error uploading file. Please check the file format and try again.');
            setMessageType('error');
        } finally {
            setUploading(false);
        }
    };

    return (
        <div className="page-container">
            <h1 className="page-title">Upload Earthquake Data</h1>
            
            <div className={`upload-section ${dragOver ? 'drag-over' : ''}`} onDrop={handleDrop} onDragOver={handleDragOver} onDragLeave={handleDragLeave}>
                <div>
                    <h3>Upload CSV File</h3>
                    <p style={{ margin: '1rem 0', color: '#666' }}>
                        Drag and drop your CSV file here, or click to select
                    </p>
                    
                    <input type="file" id="csvFile" className="file-input" accept=".csv" onChange={handleFileSelect} />
                    <label htmlFor="csvFile" className="file-label">Choose CSV File</label>
                    
                    {file && (
                        <div style={{ margin: '1rem 0' }}>
                            <p><strong>Selected file:</strong> {file.name}</p>
                            <p><strong>Size:</strong> {(file.size / 1024).toFixed(2)} KB</p>
                        </div>
                    )}
                    
                    <div>
                        <button className="upload-button" onClick={handleUpload} disabled={!file || uploading}>
                            {uploading ? 'Uploading...' : 'Upload File'}
                        </button>
                    </div>
                </div>
            </div>

            {message && (
                <div className={messageType}>{message}</div>
            )}

            <div className="info">
                <h4>CSV Format Requirements:</h4>
                <p>The CSV file should contain earthquake data with the following columns in this order:</p>
                <p><em>title, magnitude, date_time, cdi, mmi, alert, tsunami, sig, net, nst, dmin, gap, magType, depth, latitude, longitude, location, continent, country</em></p>
                <h5 style={{ marginBottom: '0.5rem', color: '#0066cc' }}>Source: <a href="https://www.kaggle.com/datasets/warcoder/earthquake-dataset" target="_blank" rel="noopener noreferrer" style={{ color: '#0066cc', textDecoration: 'underline' }}>Kaggle - Earthquake Dataset</a></h5>
                
                <div style={{ marginTop: '1rem' }}>
                    <h5 style={{ marginBottom: '0.5rem', color: '#0066cc' }}>Core Information:</h5>
                    <ul style={{ marginBottom: '1rem', paddingLeft: '1.5rem', fontSize: '0.9rem' }}>
                        <li><strong>title</strong> - Title name given to the earthquake</li>
                        <li><strong>magnitude</strong> - The magnitude of the earthquake (decimal number)</li>
                        <li><strong>date_time</strong> - Date and time in format: dd-MM-yyyy HH:mm</li>
                        <li><strong>latitude / longitude</strong> - Coordinate system for Earth's surface position (decimal numbers)</li>
                    </ul>

                    <h5 style={{ marginBottom: '0.5rem', color: '#0066cc' }}>Intensity & Impact:</h5>
                    <ul style={{ marginBottom: '1rem', paddingLeft: '1.5rem', fontSize: '0.9rem' }}>
                        <li><strong>cdi</strong> - The maximum reported intensity for the event range</li>
                        <li><strong>mmi</strong> - The maximum estimated instrumental intensity for the event</li>
                        <li><strong>alert</strong> - Alert level: "green", "yellow", "orange", or "red"</li>
                        <li><strong>tsunami</strong> - "1" for events in oceanic regions, "0" otherwise</li>
                        <li><strong>sig</strong> - Significance number (larger = more significant event)</li>
                    </ul>

                    <h5 style={{ marginBottom: '0.5rem', color: '#0066cc' }}>Technical Data:</h5>
                    <ul style={{ marginBottom: '1rem', paddingLeft: '1.5rem', fontSize: '0.9rem' }}>
                        <li><strong>net</strong> - Data contributor ID (preferred source network)</li>
                        <li><strong>nst</strong> - Total number of seismic stations used for location determination</li>
                        <li><strong>dmin</strong> - Horizontal distance from epicenter to nearest station (degrees)</li>
                        <li><strong>gap</strong> - Largest azimuthal gap between stations (degrees, &lt;180° = more reliable)</li>
                        <li><strong>depth</strong> - The depth where the earthquake begins to rupture (km)</li>
                        <li><strong>magType</strong> - Method/algorithm used to calculate preferred magnitude</li>
                    </ul>

                    <h5 style={{ marginBottom: '0.5rem', color: '#0066cc' }}>Geographic Information:</h5>
                    <ul style={{ paddingLeft: '1.5rem', fontSize: '0.9rem' }}>
                        <li><strong>location</strong> - Location within the country</li>
                        <li><strong>continent</strong> - Continent of the earthquake-affected country</li>
                        <li><strong>country</strong> - Affected country name</li>
                    </ul>
                </div>
                
                <div style={{ marginTop: '1rem', padding: '0.75rem', backgroundColor: '#fff3cd', border: '1px solid #ffeaa7', borderRadius: '4px', fontSize: '0.85rem' }}>
                    <strong>Note:</strong> Not all columns are required. The <strong>required fields</strong> are: <em>title, magnitude, date_time, latitude, longitude</em>. 
                    All other fields are optional and the system will handle missing data by displaying "N/A" for empty fields.
                </div>
            </div>
        </div>
    );
};

export default UploadPage;