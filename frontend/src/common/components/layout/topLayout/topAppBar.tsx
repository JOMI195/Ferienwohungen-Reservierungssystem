import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import AdbIcon from '@mui/icons-material/Adb';
import { Link } from 'react-router-dom';
import { appBarMenuItems } from '@/assets/appBarMenu';
import LogoIcon from './logoIcon';

const pages = ['Products', 'Pricing', 'Blog'];
const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];

function TopAppBar() {
    const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(null);
    const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);

    const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorElNav(event.currentTarget);
    };
    const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseNavMenu = () => {
        setAnchorElNav(null);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    return (
        <AppBar color="inherit" position="sticky" sx={{ height: theme => theme.layout.appbar.height }}>
            <Toolbar sx={{ height: "100%", display: "flex", flexDirection: "row", alignItems: "center", justifyContent: "space-between" }}>
                <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' }, flexDirection: "row", alignItems: "center" }}>
                    <LogoIcon color="secondary" fontSize="large" sx={{ mr: 1 }} />
                    <Typography
                        variant="h6"
                        noWrap
                        component="a"
                        href="/"
                        color="secondary"
                        sx={{
                            fontWeight: 700,
                            textDecoration: 'none',
                        }}
                    >
                        StayFinder
                    </Typography>
                </Box>
                <Box sx={{ flexGrow: 0, display: { xs: 'none', md: 'flex' }, mr: 5 }}>
                    {appBarMenuItems.map((item) => (
                        <Button
                            key={item.name}
                            component={Link}
                            to={item.url}
                            onClick={handleCloseNavMenu}
                            sx={{
                                my: 2,
                                textDecoration: 'none',
                                color: 'inherit',
                                minHeight: 48,
                                borderRadius: theme => theme.shape.borderRadius,
                            }}
                        >
                            {item.name}
                        </Button>
                    ))}
                </Box>

                <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
                    <IconButton
                        size="large"
                        aria-label="account of current user"
                        aria-controls="menu-appbar"
                        aria-haspopup="true"
                        onClick={handleOpenNavMenu}
                        color="inherit"
                    >
                        <MenuIcon />
                    </IconButton>
                    <Menu
                        id="menu-appbar"
                        anchorEl={anchorElNav}
                        anchorOrigin={{
                            vertical: 'bottom',
                            horizontal: 'left',
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'left',
                        }}
                        open={Boolean(anchorElNav)}
                        onClose={handleCloseNavMenu}
                        sx={{
                            display: { xs: 'block', md: 'none' },
                        }}
                    >
                        {appBarMenuItems.map((item) => (
                            <MenuItem href={item.url} key={item.name} onClick={handleCloseNavMenu}>
                                <Typography textAlign="center">{item.name}</Typography>
                            </MenuItem>
                        ))}
                    </Menu>
                </Box>
                <Box sx={{ display: { xs: 'flex', md: 'none' }, flexDirection: "row", alignItems: "center" }}>
                    <LogoIcon color="secondary" fontSize="large" sx={{ mr: 1 }} />
                    <Typography
                        variant="h6"
                        noWrap
                        component="a"
                        href="/"
                        color="secondary"
                        sx={{
                            fontWeight: 700,
                            textDecoration: 'none',
                        }}
                    >
                        StayFinder
                    </Typography>
                </Box>

                <Box>
                    <Tooltip title="Open settings">
                        <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                            <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
                        </IconButton>
                    </Tooltip>
                    <Menu
                        sx={{ mt: '45px' }}
                        id="menu-appbar"
                        anchorEl={anchorElUser}
                        anchorOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        open={Boolean(anchorElUser)}
                        onClose={handleCloseUserMenu}
                    >
                        {settings.map((setting) => (
                            <MenuItem key={setting} onClick={handleCloseUserMenu}>
                                <Typography textAlign="center">{setting}</Typography>
                            </MenuItem>
                        ))}
                    </Menu>
                </Box>
            </Toolbar>
        </AppBar>
    );
}
export default TopAppBar;
