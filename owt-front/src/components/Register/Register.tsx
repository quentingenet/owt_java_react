import { Person2, VisibilityOff, Visibility, Email, CalendarMonth } from '@mui/icons-material';
import { Button, Grid, IconButton, InputAdornment, Stack, Switch, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { IRegisterForm } from '../../models/IRegisterForm';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { passwordAtLeast4, passwordWithLetter, passwordWithNumber } from '../../utils/Regex';
import './Register.css';
import { useUserContext } from '../../contexts/UserContext';
import { register as registerService } from '../../services/UserService';
import { useNavigate } from 'react-router-dom';

export default function Register() {

	const userContext = useUserContext();
	const navigate = useNavigate();
	const [checkedGender, setCheckedGender] = useState(false);
	const [checkedMeasure, setCheckedMeasure] = useState(false);
	const [showPassword, setShowPassword] = useState(false);
 	const handleClickShowPassword = () => setShowPassword((show) => !show);
	const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
		event.preventDefault();
	};
	

	const handleChangeSwitchMeasure = (event: React.ChangeEvent<HTMLInputElement>) => {
		setCheckedMeasure(event.target.checked);
	  };
	const handleChangeSwitchGender = (event: React.ChangeEvent<HTMLInputElement>) => {
		setCheckedGender(event.target.checked);
	  };
	const initialValues: IRegisterForm = {
		username: '',
		password: '',
		passwordBis: '',
		emailUser: '',
		yearBirth: 0,
		isMale: true,
		isEuropeanUnitMeasure: true,
		bodySize: 0,
		goalWeight: 0,
	};

	const validationSchema = yup.object({
		username: yup
			.string()
			.min(3, 'Username must contain at least 3 characters.')
			.required('You must enter your pseudonyme.'),
		yearBirth: yup.number().required('You must enter your year of birth.'),
		emailUser: yup
			.string()
			.required('You must enter your email address.')
			.email('Your email address is not valid.'),
		isMale: yup.boolean().required('You must select your gender.'),
		isEuropeanUnitMeasure: yup.boolean().required('You must select an unit measure.'),
		bodySize: yup.number().required('You must enter your body size.'),
		goalWeight: yup.number().required('You must enter a goal weight.'),
		password: yup
			.string()
			.required('You must enter your password.')
			.matches(passwordWithLetter, 'Your password must contain a letter.')
			.matches(passwordWithNumber, 'Your password must contain a number.')
			.matches(passwordAtLeast4, 'Your password must contain at least 4 characters.'),
		passwordBis: yup
			.string()
			.required('You must enter your password.')
			.matches(passwordWithLetter, 'Your password must contain a letter.')
			.matches(passwordWithNumber, 'Your password must contain a number.')
			.matches(passwordAtLeast4, 'Your password must contain at least 4 characters.')
			.oneOf(
				[yup.ref('password'), ''],
				'The password confirmation must correspond to the chosen password.'
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
		isMale: watch('isMale'),
		isEuropeanUnitMeasure: watch('isEuropeanUnitMeasure'),
		bodySize: watch('bodySize'),
		goalWeight: watch('goalWeight'),
	};

	const submitRegister = (dataRegister: IRegisterForm) => {
		// TODO:appel POST api to register
		if (isValid) {
			try {
				registerService(dataRegister).then((response) => {
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
				<form onSubmit={handleSubmit(submitRegister)} className="registerFormInput">
					<Grid item marginY={3} xs={12} md={6}>
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
													<Person2 />
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
					<Grid item marginY={3} xs={12} md={6}>
						<Controller
							name="emailUser"
							control={control}
							defaultValue=""
							render={({ field }) => (
								<TextField
									{...field}
									id="emailUser"
									label="Email"
									type="text"
									variant="outlined"
									error={Boolean(errors.emailUser)}
									InputProps={{
										endAdornment: (
											<InputAdornment position="end">
									
													<Email />
										
											</InputAdornment>
										),
									}}
								/>
							)}
						/>
					{errors.emailUser && (
					<Grid item xs={12}>
						<span className="errorText">{errors.emailUser.message}</span>
					</Grid>
					)}
					</Grid>
					<Grid item marginY={3} xs={12} md={6}>
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
							error={Boolean(errors.password)}
							InputProps={{
								endAdornment: (
									<InputAdornment position="end">
										<IconButton
											aria-label="toggle password visibility"
											onClick={handleClickShowPassword}
											onMouseDown={handleMouseDownPassword}
											edge="end"
										>
											{showPassword ? <VisibilityOff /> : <Visibility />}
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
					<Grid item marginY={3} xs={12} md={6}>
					<Controller
							name="passwordBis"
							control={control}
							defaultValue=""
							render={({ field }) => (
						<TextField
							{...field}
							id="passwordBis"
							label="Password confirmation"
							type={showPassword ? 'text' : 'password'}
							variant="outlined"
							error={Boolean(errors.passwordBis)}
							InputProps={{
								endAdornment: (
									<InputAdornment position="end">
										<IconButton
											aria-label="toggle password visibility"
											onClick={handleClickShowPassword}
											onMouseDown={handleMouseDownPassword}
											edge="end"
										>
											{showPassword ? <VisibilityOff /> : <Visibility />}
										</IconButton>
									</InputAdornment>
								),
							}}
							/>
							)}
						/>
					{errors.passwordBis && (
					<Grid item xs={12}>
						<span className="errorText">{errors.passwordBis.message}</span>
					</Grid>
					)}						
					</Grid>

					<Grid item marginY={3} xs={12} md={6}>
					<Controller
							name="yearBirth"
							control={control}
							render={({ field }) => (
						<TextField
							{...field}
							id="yearBirth"
							label="Year birth"
							type="number"
							variant="outlined"
							error={Boolean(errors.yearBirth)}
							InputProps={{
								endAdornment: (
									<InputAdornment position="end">
										<CalendarMonth/>
									</InputAdornment>
								),
							}}
							/>
							)}
						/>
					{errors.yearBirth && (
					<Grid item xs={12}>
						<span className="errorText">{errors.yearBirth.message}</span>
					</Grid>
					)}						
					</Grid>

					<Grid item marginY={3} xs={12} md={6}>
					<Controller
							name="bodySize"
							control={control}
							render={({ field }) => (
						<TextField
							{...field}
							id="bodySize"
							label="Body size"
							aria-valuemin={120}
							type="number"
							variant="outlined"
							error={Boolean(errors.bodySize)}
							InputProps={{
								endAdornment: (
									<InputAdornment position="end">
									{"Cm"}
									</InputAdornment>
								),
							}}
							/>
							)}
						/>
					{errors.bodySize && (
					<Grid item xs={12}>
						<span className="errorText">{errors.bodySize.message}</span>
					</Grid>
					)}						
					</Grid>

					<Grid item marginY={3} xs={12} md={6}>
					<Controller
							name="goalWeight"
							control={control}
							render={({ field }) => (
						<TextField
							{...field}
							id="goalWeight"
							label="Goal weight"
							type="number"
							variant="outlined"
							error={Boolean(errors.goalWeight)}
							InputProps={{
								endAdornment: (
									<InputAdornment position="end">
									{"Kg"}
									</InputAdornment>
								),
							}}
							/>
							)}
						/>
					{errors.goalWeight && (
					<Grid item xs={12}>
						<span className="errorText">{errors.goalWeight.message}</span>
					</Grid>
					)}						
					</Grid>


					<Grid item xs={12}>
					<Stack
						direction="row"
						spacing={1}
						justifyContent={'center'}
						alignItems="center"
					>

						<Typography color={"black"} variant="h5">Female</Typography>
						<Switch
							size="medium"
							inputProps={{ 'aria-label': 'ant design' }}
							checked={checkedGender}
							onChange={handleChangeSwitchGender}
						/>
						<Typography color={"black"} variant="h5">Male</Typography>

					</Stack>
				</Grid>

				
				<Grid item xs={12}>
					<Stack
						direction="row"
						spacing={1}
						justifyContent={'center'}
						alignItems="center"
					>
						<Typography color={"black"} variant="h5">{"US/UK measures (Lbs/In)? "}</Typography>
						<Switch
							size="medium"
							inputProps={{ 'aria-label': 'ant design' }}
							checked={checkedMeasure}
							onChange={handleChangeSwitchMeasure}
						/>
						<Typography color={"black"} variant="h5">{"European measures (Kg/Cm)? "}</Typography>
					</Stack>
				</Grid>
				
					<Grid item marginY={2} xs={12}>
						<Button type="submit" variant="contained" color="primary" size="large">
							Register
						</Button>
					</Grid>
				</form>
			</Grid>
		</>
	);
}
