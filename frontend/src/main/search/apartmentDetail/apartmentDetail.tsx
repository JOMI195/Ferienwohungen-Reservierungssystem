import { getSearchtUrl } from "@/assets/appEndpoints";
import { Box, Breadcrumbs, IconButton, Link, Rating, Typography } from "@mui/material";
import { useParams } from "react-router-dom";
import { Link as RouterLink } from 'react-router-dom';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useEntitiesContext } from "@/context/entities/useEntitiesContext";
import { useEffect } from "react";

const ApartmentDetail = () => {
    const { id: ferienwohnungs_id } = useParams<{ id: string }>();
    const {
        ferienwohnungen,
        refreshFerienwohnungen,
        ferienwohnungenLoading,
        bilder,
        refreshBilder,
        ausstattungen,
        refreshAusstattungen,
        laender,
        refreshLaender,
        filterFerienwohnungen,
        filteredFerienwohnungen,
        filteredFerienwohnungenLoading
    } = useEntitiesContext();

    const selectedFerienwohnung = ferienwohnungs_id !== undefined ? ferienwohnungen.find(ferienwohnung => ferienwohnung.ferienwohnungs_id === +ferienwohnungs_id) : undefined;

    if (ferienwohnungenLoading) {
        return <Typography>Loading...</Typography>;
    }

    if (!selectedFerienwohnung) {
        return <Typography>No apartment found with ID: {ferienwohnungs_id}</Typography>;
    }

    return (
        <Box p={5}>
            <Box
                sx={{ display: "flex", justifyContent: "flex-start", alignItems: "center" }}
            >
                <IconButton sx={{ mr: 1 }} aria-label="delete" size="medium" component={RouterLink} to={getSearchtUrl()}>
                    <ArrowBackIcon />
                </IconButton>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link sx={{ textDecoration: 'none' }} component={RouterLink} to={getSearchtUrl()}>
                        <Typography color="GrayText">Search</Typography>
                    </Link>
                    <Typography color="primary">Detail</Typography>
                </Breadcrumbs>
            </Box>
            <Box sx={{ mt: 5 }}>
                <Typography variant="h3">{selectedFerienwohnung.ferienwohnungsname}</Typography>
                <Box sx={{ display: "flex", justifyContent: "flex-start", alignItems: "center" }}>
                    <Typography variant="body1">{selectedFerienwohnung.landname}</Typography>
                    <Rating size="small" value={selectedFerienwohnung.avgSterne} readOnly />
                </Box>

            </Box>
        </Box >
    );
}
export default ApartmentDetail;