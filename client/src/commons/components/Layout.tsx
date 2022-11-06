import React, { ReactNode } from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Theme } from '@emotion/react';
import { Container, Paper } from '@mui/material';
import CssBaseline from '@mui/material/CssBaseline';

const theme: Theme = createTheme();

interface LayoutType {
  children: ReactNode;
}

const Layout = ({ children }: LayoutType): JSX.Element => (
  <ThemeProvider theme={theme}>
    <CssBaseline />
    <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
      <Paper
        variant="outlined"
        sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
      >
        {children}
      </Paper>
    </Container>
  </ThemeProvider>
);

export default Layout;
