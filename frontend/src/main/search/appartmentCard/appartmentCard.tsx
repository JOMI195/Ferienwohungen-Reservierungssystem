import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Box, CardActionArea, Rating } from '@mui/material';
import { Bild, Ferienwohnung } from '@/types';

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
            <CardActionArea sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                <CardMedia
                    component="img"
                    height="200"
                    image={bild != null ? bild.linkURL : "https://img.freepik.com/fotos-kostenlos/moderne-luxusapartments-spiegeln-das-futuristische-stadtwachstum-wider-das-durch-ki-generiert-wird_188544-26162.jpg?t=st=1718469182~exp=1718472782~hmac=6f7d00c0b91ef21c81c4cd4a3e6f5f9b52acab26d60a1d3256b528b1e431f62a&w=1380"}
                    alt={bild != null ? "Image of the apartment" : "Default apartment banner"}
                    sx={{
                        objectFit: 'cover',
                    }}
                />
                <CardContent sx={{ flexGrow: 1, width: "100%", display: 'flex', flexDirection: 'column', justifyContent: 'space-between', alignItems: "flex-start" }}>
                    <Box>
                        <Typography gutterBottom variant="h5" component="div">
                            {ferienwohnung.ferienwohnungsname}
                        </Typography>
                    </Box>
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
