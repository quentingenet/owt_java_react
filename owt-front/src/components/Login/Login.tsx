import {
    Button,
    Grid,
    IconButton,
    InputAdornment,
    TextField,
    Typography,
} from '@mui/material';
import { useState } from 'react';
import './Login.css';
import { Person2, Visibility, VisibilityOff } from '@mui/icons-material';
import { ILoginForm } from '../../models/ILoginForm';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { login as loginService } from '../../services/UserService';
import { useUserContext } from '../../contexts/UserContext';
import { useNavigate } from 'react-router-dom';

export default function Login() {
    const userContext = useUserContext();

    const navigate = useNavigate();
    const [forgotPassword, setForgotPassword] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const handleClickShowPassword = () => setShowPassword((show) => !show);

    const handleMouseDownPassword = (
        event: React.MouseEvent<HTMLButtonElement>
    ) => {
        event.preventDefault();
    };

    const initialValues: ILoginForm = {
        username: '',
        password: '',
    };

    const validationSchema = yup.object({
        username: yup
            .string()
            .min(3, 'Username must contain at least 3 characters.')
            .required('You must enter your username.'),
        password: yup
            .string()
            .min(4, 'Password must contain at least 4 characters.')
            .required('You must enter your password.'),
    });

    const {
        handleSubmit,
        control,
        watch,
        formState: { errors, isValid },
    } = useForm<ILoginForm>({
        defaultValues: initialValues,
        resolver: yupResolver(validationSchema),
    });

    let dataLogin: ILoginForm = {
        username: watch('username'),
        password: watch('password'),
    };

    const submitLogin = () => {
        if (isValid) {
            try {
                loginService(dataLogin).then((response) => {
                    if (response) {
                        userContext.setIsUserLoggedIn(true);
                        let localStorageJwt = localStorage.getItem('jwt') || '';
                        if (
                            localStorageJwt !== null &&
                            localStorageJwt !== '' &&
                            localStorageJwt?.startsWith('Bearer')
                        ) {
                            userContext.setJwt(localStorageJwt);
                            navigate('/dashboard');
                        }
                    }
                });
            } catch (error) {
                console.log('Incomplete form.');
            }
        }
    };

    return (
        <>
            <Grid container marginTop={3} justifyContent={'center'}>
                <form
                    onSubmit={handleSubmit(submitLogin)}
                    className='loginFormInput'
                >
                    <Grid item xs={12} marginY={3}>
                        <Controller
                            name='username'
                            control={control}
                            defaultValue=''
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    id='username'
                                    label='Username'
                                    type='text'
                                    variant='outlined'
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position='end'>
                                                <IconButton
                                                    aria-label='toggle password visibility'
                                                    edge='end'
                                                >
                                                    <Person2 />
                                                </IconButton>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            )}
                        />
                        {errors.username && (
                            <Grid item xs={12}>
                                <span className='errorText'>
                                    {errors.username.message}
                                </span>
                            </Grid>
                        )}
                    </Grid>
                    <Grid item marginY={3} xs={12}>
                        <Controller
                            name='password'
                            control={control}
                            defaultValue=''
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    id='password'
                                    label='Password'
                                    type={showPassword ? 'text' : 'password'}
                                    variant='outlined'
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position='end'>
                                                <IconButton
                                                    aria-label='toggle password visibility'
                                                    onClick={
                                                        handleClickShowPassword
                                                    }
                                                    onMouseDown={
                                                        handleMouseDownPassword
                                                    }
                                                    edge='end'
                                                >
                                                    {showPassword ? (
                                                        <VisibilityOff />
                                                    ) : (
                                                        <Visibility />
                                                    )}
                                                </IconButton>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            )}
                        />
                        {errors.password && (
                            <Grid item xs={12}>
                                <span className='errorText'>
                                    {errors.password.message}
                                </span>
                            </Grid>
                        )}
                    </Grid>
                    <Grid
                        item
                        marginY={2}
                        xs={12}
                        justifyContent={'center'}
                        flexDirection={'column'}
                        alignItems={'center'}
                    >
                        <Grid item marginBottom={2}>
                            <Button
                                type='submit'
                                variant='contained'
                                color='primary'
                                size='large'
                                disabled={forgotPassword ? true : false}
                            >
                                Login
                            </Button>
                        </Grid>
                        <span
                            className='forgot-password'
                            onClick={() => setForgotPassword(!forgotPassword)}
                        >
                            Forgot password ?
                        </span>
                        {forgotPassword && (
                            <Grid
                                container
                                xs={12}
                                justifyContent={'center'}
                                flexDirection={'column'}
                            >
                                <Grid item marginTop={2}>
                                    <Controller
                                        name='password'
                                        control={control}
                                        defaultValue=''
                                        render={({ field }) => (
                                            <TextField
                                                {...field}
                                                id='recoveryEmail'
                                                label='Recovery email'
                                                variant='outlined'
                                            />
                                        )}
                                    />
                                </Grid>
                                <Grid item marginY={2}>
                                    <Button
                                        variant='contained'
                                        color='primary'
                                        size='large'
                                        onClick={() => {
                                            //TODO: appel du backend pour reset le mot de passe + page reset password
                                            setForgotPassword(false);
                                            navigate('/reset-password');
                                        }}
                                    >
                                        SEND RECOVERY EMAIL
                                    </Button>
                                </Grid>
                            </Grid>
                        )}
                    </Grid>
                </form>
            </Grid>
        </>
    );
}
