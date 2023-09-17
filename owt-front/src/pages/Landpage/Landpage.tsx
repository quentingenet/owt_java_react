import { Grid, Stack, Switch, Typography } from '@mui/material';
import { useState } from 'react';
import Register from '../../components/Register/Register';
import Login from '../../components/Login/Login';
import scale from '../../assets/scale-icon.png';
//import css
import './Landpage.css';
import { Link } from 'react-router-dom';
export default function Landpage() {
    const [isFirstConnection, setIsFirstConnection] = useState<boolean>(false);

    return (
        <>
            <Grid container>
                <Grid item xs={12}>
                    <div className='titleLandpage'>
                        <h1>Open Weight Tracker</h1>
                    </div>
                </Grid>
                <Grid item xs={12} display={'flex'} justifyContent={'center'}>
                    <div className='scaleIcon'>
                        <span className='iconScale'>FREE</span>
                        <img src={scale} alt='OWT' />
                        <span className='iconScale'>OPEN SOURCE</span>
                    </div>
                </Grid>
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                    <div className='descriptionLandpage'>
                        <p>
                            Track your weight... <br />
                            It's free and open source
                            <br />
                            <span className='firstTime'>
                                Is it your first time here ?
                            </span>
                        </p>
                    </div>
                </Grid>
                <Grid item xs={12}>
                    <Stack
                        direction='row'
                        spacing={1}
                        justifyContent={'center'}
                        alignItems='center'
                    >
                        <Typography variant='h5'>Login</Typography>
                        <Switch
                            size='medium'
                            inputProps={{ 'aria-label': 'ant design' }}
                            onChange={(e) =>
                                setIsFirstConnection(e.target.checked)
                            }
                        />
                        <Typography variant='h5'>Register</Typography>
                    </Stack>
                </Grid>
                <Grid item xs={12}>
                    {isFirstConnection ? <Register /> : <Login />}
                </Grid>
                <Grid
                    item
                    xs={12}
                    marginTop={3}
                    justifyContent={'center'}
                    textAlign={'center'}
                >
                    <Grid item>
                        <Link to='/Contact'>Contact</Link> |{' '}
                        <Link
                            to={'https://github.com/quentingenet/owt'}
                            target='_blank'
                            rel='noopener noreferrer'
                        >
                            Git repository
                        </Link>{' '}
                        |{' '}
                        <Link
                            to={'/owt-terms-and-conditions'}
                            target='_blank'
                            rel='noopener noreferrer'
                        >
                            Terms and conditions
                        </Link>{' '}
                        |{' '}
                        <Link
                            to={'https://www.gnu.org/licenses/copyleft.en.html'}
                            target='_blank'
                            rel='noopener noreferrer'
                        >
                            Copyleft
                        </Link>
                    </Grid>
                </Grid>
            </Grid>
        </>
    );
}
