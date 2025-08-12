import React, { useState, useEffect } from 'react';
import axios from 'axios';

const InspectPage = () => {
    const [earthquakes, setEarthquakes] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);

    const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

    const fetchEarthquakes = async (page = 0, size = 10) => {
        setLoading(true);
        setError('');

        try {
            const response = await axios.get(`${API_BASE_URL}/pageable`, {
                params: {
                page: page,
                size: size
                }
            });

            // Handle different response structures
            if (response.data.content) {
                // Spring Boot pageable response
                setEarthquakes(response.data.content);
                setTotalPages(response.data.totalPages);
                setTotalElements(response.data.totalElements);
                setCurrentPage(response.data.number);
            } else if (Array.isArray(response.data)) {
                // Simple array response
                setEarthquakes(response.data);
                setTotalPages(1);
                setTotalElements(response.data.length);
                setCurrentPage(0);
            } else {
                setEarthquakes([]);
                setTotalPages(0);
                setTotalElements(0);
            }
        } catch (error) {
            console.error('Error fetching earthquakes:', error);
            setError('Failed to fetch earthquake data. Please make sure the API is running.');
            setEarthquakes([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEarthquakes(currentPage, pageSize);
    }, []);

    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setCurrentPage(newPage);
            fetchEarthquakes(newPage, pageSize);
        }
    };

    const handlePageSizeChange = (event) => {
        const newSize = parseInt(event.target.value);
        setPageSize(newSize);
        setCurrentPage(0);
        fetchEarthquakes(0, newSize);
    };

    const formatDateTime = (dateTime) => {
        if (!dateTime) return 'N/A';
        try {
            // Handle different datetime formats
            const date = new Date(dateTime);
            if (isNaN(date.getTime())) {
                return dateTime; // Return original if can't parse
            }
            return date.toLocaleString();
        } catch {
            return dateTime;
        }
    };

    const formatNumber = (num, decimals = 3) => {
        if (num === null || num === undefined) return 'N/A';
        return parseFloat(num).toFixed(decimals);
    };

    return (
        <div className="page-container">
            <h1 className="page-title">Inspect Earthquake Data</h1>
            
            <div className="controls">
                <div className="pagination">
                <button onClick={() => handlePageChange(0)} disabled={currentPage === 0}>First</button>
                <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0}>Previous</button>
                <span style={{ margin: '0 1rem' }}>
                    Page {currentPage + 1} of {totalPages} ({totalElements} total records)
                </span>
                <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage >= totalPages - 1}>Next</button>
                <button onClick={() => handlePageChange(totalPages - 1)} disabled={currentPage >= totalPages - 1}>Last</button>
                
                <select value={pageSize} onChange={handlePageSizeChange}>
                    <option value={5}>5 per page</option>
                    <option value={10}>10 per page</option>
                    <option value={25}>25 per page</option>
                    <option value={50}>50 per page</option>
                    <option value={100}>100 per page</option>
                </select>
                </div>
            </div>

            {error && <div className="error">{error}</div>}

            {loading ? (
                <div className="loading">Loading earthquake data...</div>
            ) : (
                <>
                {earthquakes.length === 0 ? (
                    <div className="info">
                    <p>No earthquake data found. Try uploading a CSV file first.</p>
                    </div>
                ) : (
                    <div className="table-container">
                        <table className="earthquake-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Magnitude</th>
                                    <th>Date & Time</th>
                                    <th>CDI</th>
                                    <th>MMI</th>
                                    <th>Alert</th>
                                    <th>Tsunami</th>
                                    <th>Sig</th>
                                    <th>Net</th>
                                    <th>NST</th>
                                    <th>DMin</th>
                                    <th>Gap</th>
                                    <th>Mag Type</th>
                                    <th>Depth</th>
                                    <th>Latitude</th>
                                    <th>Longitude</th>
                                    <th>Location</th>
                                    <th>Continent</th>
                                    <th>Country</th>
                                </tr>
                            </thead>
                            <tbody>
                                {earthquakes.map((earthquake) => (
                                    <tr key={earthquake.id || earthquake.earthquakeId}>
                                    <td>{earthquake.id || earthquake.earthquakeId}</td>
                                    <td title={earthquake.title} style={{ maxWidth: '225px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                                        {earthquake.title}
                                    </td>
                                    <td>{formatNumber(earthquake.magnitude, 1)}</td>
                                    <td style={{ minWidth: '125px' }}>{formatDateTime(earthquake.dateTime)}</td>
                                    <td>{earthquake.cdi || 'N/A'}</td>
                                    <td>{earthquake.mmi || 'N/A'}</td>
                                    <td>
                                        <span className={`alert-badge ${earthquake.alert || 'none'}`}>
                                        {earthquake.alert || 'None'}
                                        </span>
                                    </td>
                                    <td>
                                        <span className={`tsunami-badge ${earthquake.tsunami ? 'yes' : 'no'}`}>
                                        {earthquake.tsunami ? 'Yes' : 'No'}
                                        </span>
                                    </td>
                                    <td>{earthquake.sig || 'N/A'}</td>
                                    <td>{earthquake.net || 'N/A'}</td>
                                    <td>{earthquake.nst || 'N/A'}</td>
                                    <td>{formatNumber(earthquake.dmin, 3)}</td>
                                    <td>{earthquake.gap || 'N/A'}</td>
                                    <td>{earthquake.magType || earthquake.magtype || 'N/A'}</td>
                                    <td>{formatNumber(earthquake.depth, 3)}</td>
                                    <td>{formatNumber(earthquake.latitude)}</td>
                                    <td>{formatNumber(earthquake.longitude)}</td>
                                    <td title={earthquake.location} style={{ maxWidth: '200px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                                        {earthquake.location || 'N/A'}
                                    </td>
                                    <td>{earthquake.continent || 'N/A'}</td>
                                    <td>{earthquake.country || 'N/A'}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
                </>
            )}
        </div>
    );
};

export default InspectPage;