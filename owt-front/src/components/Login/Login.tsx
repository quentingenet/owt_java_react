import { Button, Grid, IconButton, InputAdornment, TextField } from '@mui/material';
import { useState } from 'react';
import './Login.css';
import { Visibility, VisibilityOff, Person2 } from '@mui/icons-material';
import { ILoginForm } from '../../models/ILoginForm';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';

export default function Login() {
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
		formState: { errors },
	} = useForm<ILoginForm>({
		defaultValues: initialValues,
		resolver: yupResolver(validationSchema),
	});

	const dataLogin: ILoginForm = {
		username: watch('username'),
		password: watch('password'),
	};
	const submitLogin = () => {
		console.log('DATA LOGIN : ', dataLogin);
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
									error={Boolean(errors.username)}
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
