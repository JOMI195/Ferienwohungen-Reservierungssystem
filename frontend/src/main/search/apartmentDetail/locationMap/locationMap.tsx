import React, { useEffect, useState } from 'react';
import { Box } from '@mui/material';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

interface LocationMapProps {
    address: string;
}

const LocationMap: React.FC<LocationMapProps> = ({ address }) => {
    const [position, setPosition] = useState<[number, number] | null>(null);


    const fetchCoordinates = async (address: string) => {
        try {
            //const response = await fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(address)}`);
            /* const data = await response.json();
            const { lat, lon } = data[0];
            setPosition([parseFloat(lat), parseFloat(lon)]); */
            setPosition([51.505, -0.09]);
        } catch (error) {
            console.error('Error fetching coordinates:', error);
        }
    };

    useEffect(() => {
        fetchCoordinates(address);
    }, [address]);

    useEffect(() => {

    }, [position]);

    return (
        <Box sx={{ height: '400px', marginTop: '20px' }}>
            {position && <MapContainer center={position || [0, 0]} zoom={position ? 30 : 1} scrollWheelZoom={true} style={{ height: '100%', width: '100%' }}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {position && (
                    <Marker position={position}>
                        <Popup>
                            Location
                        </Popup>
                    </Marker>
                )}
            </MapContainer>}
        </Box>
    );
};

export default LocationMap;
