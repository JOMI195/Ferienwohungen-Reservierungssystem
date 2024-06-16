import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';
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
        <Card elevation={1} sx={{ height: '100%', borderRadius: theme => theme.shape.borderRadius }}>
            <CardActionArea>
                <CardMedia
                    component="img"
                    height="200"
                    image={bild != null ? bild.linkURL : "https://img.freepik.com/fotos-kostenlos/moderne-luxusapartments-spiegeln-das-futuristische-stadtwachstum-wider-das-durch-ki-generiert-wird_188544-26162.jpg?t=st=1718469182~exp=1718472782~hmac=6f7d00c0b91ef21c81c4cd4a3e6f5f9b52acab26d60a1d3256b528b1e431f62a&w=1380"}
                    alt={bild != null ? "Image of the apartment" : "Default apartment banner"}
                    sx={{
                        objectFit: 'stretch',
                    }}
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {ferienwohnung.ferienwohnungsname}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        €{ferienwohnung.mietpreis}/Nacht
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}

export default AppartmentCard;