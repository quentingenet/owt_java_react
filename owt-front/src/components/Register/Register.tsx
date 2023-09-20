import {
    Person2,
    VisibilityOff,
    Visibility,
    Email,
    CalendarMonth,
} from '@mui/icons-material';
import {
    Button,
    Checkbox,
    FormControl,
    FormControlLabel,
    Grid,
    IconButton,
    InputAdornment,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    Slider,
    Switch,
    TextField,
    Typography,
} from '@mui/material';
import { useEffect, useState } from 'react';
import { IRegisterForm } from '../../models/IRegisterForm';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import {
    passwordAtLeast4,
    passwordWithLetter,
    passwordWithNumber,
} from '../../utils/Regex';
import './Register.css';
import { useUserContext } from '../../contexts/UserContext';
import { register as registerService } from '../../services/UserService';
import { Link, useNavigate } from 'react-router-dom';

export default function Register() {
    const userContext = useUserContext();
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [gender, setGender] = useState<boolean>(false);
    const [genderStr, setGenderStr] = useState<string>('Woman');
    const handleClickShowPassword = () => setShowPassword((show) => !show);

    const handleMouseDownPassword = (
        event: React.MouseEvent<HTMLButtonElement>
    ) => {
        event.preventDefault();
    };

    const handleChangeGenderStr = (event: SelectChangeEvent) => {
        setGenderStr(event.target.value);
    };

    useEffect(() => {
        if (genderStr === 'Man') {
            setGender(true);
        } else {
            setGender(false);
        }
    }, [gender, genderStr]);

    const currentYear = new Date().getFullYear();

    const marksEU = [
        {
            value: 50,
            label: '50 Kg',
        },
        {
            value: 200,
            label: '200 Kg',
        },
    ];

    function valuetextEU(value: number) {
        return `${value}Kg`;
    }

    const initialValues: IRegisterForm = {
        username: '',
        password: '',
        passwordBis: '',
        emailUser: '',
        yearBirth: 1990,
        isMale: false,
        isEuropeanUnitMeasure: false,
        bodySize: 175,
        goalWeight: 80,
        isAcceptedTerms: false,
    };

    const validationSchema = yup.object({
        username: yup
            .string()
            .min(3, 'Username must contain at least 3 characters')
            .required('Enter your pseudonyme'),
        yearBirth: yup
            .number()
            .positive('Must be a positive number')
            .integer('Must be a correct number')
            .min(1900, 'Birth year must be greater than 1900')
            .max(currentYear, 'Birth year must be less')
            .required('Enter your year of birth'),
        emailUser: yup
            .string()
            .required('Enter your email address')
            .email('Your email address is not valid'),
        isMale: yup.boolean().required('Select your gender'),
        isAcceptedTerms: yup.boolean().required('You must accept terms'),
        isEuropeanUnitMeasure: yup.boolean().required('Select an unit measure'),
        bodySize: yup
            .number()
            .positive()
            .integer()
            .min(100)
            .max(250)
            .required('Enter your body size'),
        goalWeight: yup.number().required('Enter a goal weight'),
        password: yup
            .string()
            .required('Enter your password.')
            .matches(passwordWithLetter, 'Your password must contain a letter')
            .matches(passwordWithNumber, 'Your password must contain a number')
            .matches(
                passwordAtLeast4,
                'Your password must contain at least 4 characters'
            ),
        passwordBis: yup
            .string()
            .required('Enter your password.')
            .matches(passwordWithLetter, 'Your password must contain a letter')
            .matches(passwordWithNumber, 'Your password must contain a number')
            .matches(
                passwordAtLeast4,
                'Your password must contain at least 4 characters'
            )
            .oneOf(
                [yup.ref('password'), ''],
                'The password confirmation must correspond to the chosen password'
            ),
    });

    const {
        handleSubmit,
        control,
        watch,
        formState: { errors, isValid },
    } = useForm<IRegisterForm>({
        defaultValues: initialValues,
        resolver: yupResolver(validationSchema),
    });

    const dataRegister: IRegisterForm = {
        username: watch('username'),
        password: watch('password'),
        passwordBis: watch('passwordBis'),
        emailUser: watch('emailUser'),
        yearBirth: watch('yearBirth'),
        isMale: gender,
        isEuropeanUnitMeasure: watch('isEuropeanUnitMeasure'),
        bodySize: watch('bodySize'),
        goalWeight: watch('goalWeight'),
        isAcceptedTerms: watch('isAcceptedTerms'),
    };

    const submitRegister = () => {
        if (isValid) {
            try {
                console.log('IS MALE BOOLEAN===', dataRegister.isMale);
                registerService(dataRegister).then((response) => {
                    if (response) {
                        const localStorageJwt =
                            localStorage.getItem('jwt') || '';
                        userContext.setJwt(localStorageJwt);
                        userContext.setIsUserLoggedIn(true);
                        navigate('/dashboard');
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
                    onSubmit={handleSubmit(submitRegister)}
                    className='register-form-input'
                >
                    <Grid item marginY={3} xs={12}>
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
                                    error={Boolean(errors.username)}
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position='end'>
                                                <Person2 />
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
                            name='emailUser'
                            control={control}
                            defaultValue=''
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    id='emailUser'
                                    label='Email'
                                    type='text'
                                    variant='outlined'
                                    error={Boolean(errors.emailUser)}
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position='end'>
                                                <Email />
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            )}
                        />
                        {errors.emailUser && (
                            <Grid item xs={12}>
                                <span className='errorText'>
                                    {errors.emailUser.message}
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
                                    error={Boolean(errors.password)}
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
                    <Grid item marginY={3} xs={12}>
                        <Controller
                            name='passwordBis'
                            control={control}
                            defaultValue=''
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    id='passwordBis'
                                    label='Password confirmation'
                                    type={showPassword ? 'text' : 'password'}
                                    variant='outlined'
                                    error={Boolean(errors.passwordBis)}
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
                        {errors.passwordBis && (
                            <Grid item xs={12}>
                                <span className='errorText'>
                                    {errors.passwordBis.message}
                                </span>
                            </Grid>
                        )}
                    </Grid>

                    <Grid item marginY={3} xs={12}>
                        <Controller
                            name='yearBirth'
                            control={control}
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    id='yearBirth'
                                    label='Year birth'
                                    type='number'
                                    variant='outlined'
                                    error={Boolean(errors.yearBirth)}
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position='end'>
                                                <CalendarMonth />
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            )}
                        />
                        {errors.yearBirth && (
                            <Grid item xs={12}>
                                <span className='errorText'>
                                    {errors.yearBirth.message}
                                </span>
                            </Grid>
                        )}
                    </Grid>

                    <Grid container justifyContent={'center'}>
                        <Grid item xs={7} lg={5}>
                            <Controller
                                name='isMale'
                                control={control}
                                defaultValue={false}
                                render={({ field }) => (
                                    <FormControl fullWidth>
                                        <InputLabel id='demo-simple-select-label'>
                                            Gender
                                        </InputLabel>
                                        <Select
                                            {...field}
                                            labelId='demo-simple-select-label'
                                            id='demo-simple-select'
                                            value={genderStr}
                                            label='Gender'
                                            onChange={handleChangeGenderStr}
                                        >
                                            <MenuItem value={'Man'}>
                                                Man
                                            </MenuItem>
                                            <MenuItem value={'Woman'}>
                                                Woman
                                            </MenuItem>
                                            <MenuItem value={'Neutral'}>
                                                Neutral
                                            </MenuItem>
                                        </Select>
                                    </FormControl>
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid item xs={12} marginY={2}>
                        <Grid item xs={12}>
                            <Typography color={'#555458'}>
                                Units measurements
                            </Typography>
                        </Grid>
                        <Grid
                            container
                            justifyContent={'center'}
                            alignItems={'center'}
                        >
                            <Typography color={'black'}>{'Lbs/In'}</Typography>
                            <Controller
                                name='isEuropeanUnitMeasure'
                                control={control}
                                defaultValue={false}
                                render={({ field }) => (
                                    <Switch
                                        {...field}
                                        size='medium'
                                        inputProps={{
                                            'aria-label': 'ant design',
                                        }}
                                        checked={watch('isEuropeanUnitMeasure')}
                                    />
                                )}
                            />

                            <Typography color={'black'}>{'Kg/Cm'}</Typography>
                        </Grid>
                    </Grid>
                    <Grid item marginY={3} xs={12}>
                        <Controller
                            name='bodySize'
                            control={control}
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    id='bodySize'
                                    label='Body size (cm or inch)'
                                    type='number'
                                    variant='outlined'
                                    error={Boolean(errors.bodySize)}
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position='end'>
                                                {watch('isEuropeanUnitMeasure')
                                                    ? 'cm'
                                                    : 'inch'}
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            )}
                        />
                        {errors.bodySize && (
                            <Grid item xs={12}>
                                <span className='errorText'>
                                    {errors.bodySize.message}
                                </span>
                            </Grid>
                        )}
                    </Grid>

                    <Grid item xs={12}>
                        <Typography color={'#555458'}>
                            {'Goal weight'}
                        </Typography>
                    </Grid>
                    <Grid
                        item
                        xs={12}
                        justifyContent={'center'}
                        alignItems={'center'}
                        paddingX={20}
                        marginTop={4}
                    >
                        <Controller
                            name='goalWeight'
                            control={control}
                            render={({ field }) => (
                                <Slider
                                    {...field}
                                    aria-label='Always visible'
                                    size='medium'
                                    defaultValue={85}
                                    min={50}
                                    max={200}
                                    getAriaValueText={valuetextEU}
                                    step={1}
                                    value={watch('goalWeight')}
                                    marks={marksEU}
                                    valueLabelDisplay='on'
                                />
                            )}
                        />
                    </Grid>
                    <Grid
                        container
                        justifyContent={'center'}
                        alignItems={'center'}
                        marginTop={3}
                    >
                        <Grid
                            item
                            justifyContent={'center'}
                            sx={{ color: 'black', fontSize: '0.8em' }}
                        >
                            <Typography>
                                I confirm to have read, understand and accepted
                            </Typography>
                            <Typography>
                                <Link
                                    style={{
                                        color: 'black',
                                        fontWeight: 'bold',
                                    }}
                                    to={'/owt-terms-and-conditions'}
                                    target='_blank'
                                    rel='noopener noreferrer'
                                >
                                    Terms and conditions
                                </Link>
                            </Typography>
                        </Grid>
                        <Grid item xs={12} my={3} sx={{ color: 'black' }}>
                            <Controller
                                name='isAcceptedTerms'
                                control={control}
                                render={({ field }) => (
                                    <FormControlLabel
                                        {...field}
                                        required={
                                            watch('isAcceptedTerms')
                                                ? false
                                                : true
                                        }
                                        label={'I agree'}
                                        value={true}
                                        control={<Checkbox />}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid item marginY={2} xs={12}>
                        <Button
                            type='submit'
                            variant='contained'
                            color='primary'
                            size='large'
                        >
                            Register
                        </Button>
                    </Grid>
                </form>
            </Grid>
        </>
    );
}
