import { Button, Grid, IconButton, InputAdornment, TextField } from '@mui/material';
import { useEffect, useState } from 'react';
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

	let localStorageJwt = localStorage.getItem('jwt');

	useEffect(() => {
		console.log('USER CONTEXT FROM APP before: ', userContext.isUserLoggedIn);
		if (localStorageJwt !== null && localStorageJwt !== '') {
			userContext.setJwt(localStorageJwt);
		}
		if (localStorageJwt?.startsWith('Bearer', 0)) {
			console.log('START WITH BEARER', localStorageJwt?.startsWith('Bearer'));
			userContext.setIsUserLoggedIn(true);
		}
		console.log('JWT USER USE EFFECT', userContext.jwt);
		console.log('USER CONTEXT from app useEffect: ', userContext.isUserLoggedIn);
	}, [userContext, localStorage]);

	const navigate = useNavigate();

	const [showPassword, setShowPassword] = useState(false);

	const handleClickShowPassword = () => setShowPassword((show) => !show);

	const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
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
			loginService(dataLogin);
			userContext.isUserLoggedIn = true;
			userContext.jwt = localStorage.getItem('jwt') || '';
			console.log('USER CONTEXT', userContext);
			navigate('/dashboard');
		} else {
			console.log('Incomplete form.');
		}
	};

	return (
		<>
			<Grid container marginTop={3} justifyContent={'center'}>
				<form onSubmit={handleSubmit(submitLogin)} className="loginFormInput">
					<Grid item xs={12} marginY={3}>
						<Controller
							name="username"
							control={control}
							defaultValue=""
							render={({ field }) => (
								<TextField
									{...field}
									id="username"
									label="Username"
									type="text"
									variant="outlined"
									InputProps={{
										endAdornment: (
											<InputAdornment position="end">
												<IconButton
													aria-label="toggle password visibility"
													edge="end"
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
								<span className="errorText">{errors.username.message}</span>
							</Grid>
						)}
					</Grid>
					<Grid item marginY={3} xs={12}>
						<Controller
							name="password"
							control={control}
							defaultValue=""
							render={({ field }) => (
								<TextField
									{...field}
									id="password"
									label="Password"
									type={showPassword ? 'text' : 'password'}
									variant="outlined"
									InputProps={{
										endAdornment: (
											<InputAdornment position="end">
												<IconButton
													aria-label="toggle password visibility"
													onClick={handleClickShowPassword}
													onMouseDown={handleMouseDownPassword}
													edge="end"
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
								<span className="errorText">{errors.password.message}</span>
							</Grid>
						)}
					</Grid>
					<Grid item marginY={2} xs={12}>
						<Button type="submit" variant="contained" color="primary" size="large">
							Login
						</Button>
					</Grid>
				</form>
			</Grid>
		</>
	);
}
