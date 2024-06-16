import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

function Copyright() {
    return (
        <Typography variant="body2" color="text.secondary" align="center">
            {"Made with ❤️ "}
            {new Date().getFullYear()}
        </Typography>
    );
}

function Footer() {
    return (
        <Box component="footer"
            sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                bgcolor: 'background.paper',
                height: theme => theme.layout.footer.height
            }}>
            <Copyright />
        </Box>
    );
}

export default Footer;