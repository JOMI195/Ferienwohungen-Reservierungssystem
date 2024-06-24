import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Box, CardActionArea, Rating } from '@mui/material';
import { Bild, Ferienwohnung } from '@/types';
import { getApartmentDetailUrl } from '@/assets/appEndpoints';
import { Link as RouterLink } from 'react-router-dom';

interface AppartmentCardProps {
    ferienwohnung: Ferienwohnung;
    bild: Bild | null;
}

const AppartmentCard: React.FC<AppartmentCardProps> = ({
    ferienwohnung,
    bild
}) => {
    return (
        <Card
            elevation={1}
            sx={{
                width: "100%",
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                borderRadius: theme => theme.shape.borderRadius
            }}
        >
            <CardActionArea
                component={RouterLink}
                to={getApartmentDetailUrl(ferienwohnung.ferienwohnungs_id)}
                sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column' }}
            >
                <CardMedia
                    component="img"
                    height="200"
                    image={bild != null ? bild.linkURL : "/imagePlaceholder.svg"}
                    alt={bild != null ? "Image of the apartment" : "Default apartment banner"}
                    sx={{
                        objectFit: 'cover',
                    }}
                />
                <CardContent sx={{ flexGrow: 1, width: "100%", display: 'flex', flexDirection: 'column', justifyContent: 'space-between', alignItems: "flex-start" }}>
                    <Box sx={{ display: 'flex', justifyContent: "center" }}>
                        <Typography gutterBottom variant="body2" component="div">
                            {`${ferienwohnung.ort} - ${ferienwohnung.landname}`}
                        </Typography>
                    </Box>
                    <Typography gutterBottom variant="h5" component="div">
                        {ferienwohnung.ferienwohnungsname}
                    </Typography>
                    <Box sx={{ width: "100%", display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
                        <Typography variant="body2" color="text.secondary">
                            €{ferienwohnung.mietpreis}/Nacht
                        </Typography>
                        <Rating size="small" value={ferienwohnung.avgSterne} readOnly />
                    </Box>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}

export default AppartmentCard;
