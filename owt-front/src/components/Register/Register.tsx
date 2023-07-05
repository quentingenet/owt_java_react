import { Person2, VisibilityOff, Visibility } from '@mui/icons-material';
import { Button, Grid, IconButton, InputAdornment, TextField } from '@mui/material';
import { useState } from 'react';
import { IRegisterForm } from '../../models/IRegisterForm';
import { Controller, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { passwordAtLeast4, passwordWithLetter, passwordWithNumber } from '../../utils/Regex';

export default function Register() {
	const [showPassword, setShowPassword] = useState(false);

	const handleClickShowPassword = () => setShowPassword((show) => !show);
	const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
		event.preventDefault();
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
		formState: { errors },
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
	};

	return (
		<>
			<Grid container marginTop={3} justifyContent={'center'}>
				<form onSubmit={handleSubmit(submitRegister)} className="loginFormInput">
					<Grid item marginY={3} xs={12}>
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
									required
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
					</Grid>
					<Grid item marginY={3} xs={12}>
						<TextField
							id="password"
							label="Password"
							type={showPassword ? 'text' : 'password'}
							variant="outlined"
							required
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
