import {
    Alert,
    Button,
    Collapse,
    Grid,
    IconButton,
    InputAdornment,
    TextField,
} from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { useState } from 'react';
import './Login.css';
import { Email, Person2, Visibility, VisibilityOff } from '@mui/icons-material';
import { ILoginForm } from '../../models/ILoginForm';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { login as loginService } from '../../services/UserService';
import { useUserContext } from '../../contexts/UserContext';
import { useNavigate } from 'react-router-dom';
import { emailValidator } from '../../utils/Regex';

export default function Login() {
    const userContext = useUserContext();

    const navigate = useNavigate();
    const [forgotPassword, setForgotPassword] = useState<boolean>(false);
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [isMailSended, setIsMailSended] = useState<boolean>(false);
    const [errorRecovery, setErrorRecovery] = useState<boolean>(false);
    const [emailRecovery, setEmailrecovery] = useState<string>('');
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

    const dataLogin: ILoginForm = {
        username: watch('username'),
        password: watch('password'),
    };

    const submitLogin = () => {
        if (isValid) {
            try {
                loginService(dataLogin).then((response) => {
                    if (response) {
                        userContext.setIsUserLoggedIn(true);
                        const localStorageJwt =
                            localStorage.getItem('jwt') || '';
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
                    <Grid container marginTop={2} justifyContent={'center'}>
                        <Grid item xs={10} marginY={1}>
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
                    </Grid>
                    <Grid container marginTop={1} justifyContent={'center'}>
                        <Grid item xs={10}>
                            <Controller
                                name='password'
                                control={control}
                                defaultValue=''
                                render={({ field }) => (
                                    <TextField
                                        {...field}
                                        id='password'
                                        label='Password'
                                        type={
                                            showPassword ? 'text' : 'password'
                                        }
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
                    </Grid>

                    <Grid
                        container
                        marginY={2}
                        justifyContent={'center'}
                        flexDirection={'column'}
                        alignItems={'center'}
                    >
                        <Grid item xs={10} marginBottom={2}>
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
                                justifyContent={'center'}
                                flexDirection={'column'}
                            >
                                <Grid container justifyContent={'center'}>
                                    <Grid item xs={10} marginTop={2}>
                                        <TextField
                                            id='emailRecovery'
                                            label='Email'
                                            variant='outlined'
                                            onChange={(event) =>
                                                setEmailrecovery(
                                                    event.target.value
                                                )
                                            }
                                            value={emailRecovery}
                                            InputProps={{
                                                endAdornment: (
                                                    <InputAdornment position='end'>
                                                        <Email />
                                                    </InputAdornment>
                                                ),
                                            }}
                                        />
                                        /
                                    </Grid>
                                </Grid>
                                <Grid container justifyContent={'center'}>
                                    <Grid item xs={10} marginY={2}>
                                        <Button
                                            variant='contained'
                                            color='primary'
                                            size='large'
                                            onClick={() => {
                                                //TODO: appel du backend pour reset le mot de passe + page reset password
                                                if (
                                                    emailRecovery.match(
                                                        emailValidator
                                                    )
                                                ) {
                                                    setIsMailSended(true);
                                                    setErrorRecovery(false);
                                                } else {
                                                    setErrorRecovery(true);
                                                }
                                            }}
                                        >
                                            SEND RECOVERY EMAIL
                                        </Button>
                                    </Grid>
                                </Grid>
                                {errorRecovery && (
                                    <Grid item marginY={2}>
                                        <Collapse in={errorRecovery}>
                                            <Alert
                                                severity='error'
                                                action={
                                                    <IconButton
                                                        aria-label='close'
                                                        color='inherit'
                                                        size='small'
                                                        onClick={() => {
                                                            setErrorRecovery(
                                                                false
                                                            );
                                                            setForgotPassword(
                                                                false
                                                            );
                                                        }}
                                                    >
                                                        <CloseIcon fontSize='inherit' />
                                                    </IconButton>
                                                }
                                                sx={{ mb: 2 }}
                                            >
                                                Error, email not sended
                                            </Alert>
                                        </Collapse>
                                    </Grid>
                                )}
                                {isMailSended && (
                                    <Grid item marginY={2}>
                                        <Collapse in={isMailSended}>
                                            <Alert
                                                action={
                                                    <IconButton
                                                        aria-label='close'
                                                        color='inherit'
                                                        size='small'
                                                        onClick={() => {
                                                            setIsMailSended(
                                                                false
                                                            );
                                                            setForgotPassword(
                                                                false
                                                            );
                                                        }}
                                                    >
                                                        <CloseIcon fontSize='inherit' />
                                                    </IconButton>
                                                }
                                                sx={{
                                                    mb: 2,
                                                }}
                                            >
                                                Mail is sended. Maybe check your
                                                spams folder if you don't see it
                                                from inbox
                                            </Alert>
                                        </Collapse>
                                    </Grid>
                                )}
                            </Grid>
                        )}
                    </Grid>
                </form>
            </Grid>
        </>
    );
}
