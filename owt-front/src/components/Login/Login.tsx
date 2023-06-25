import { Button, Grid, IconButton, InputAdornment, TextField } from '@mui/material';
import { FormEvent, useState } from 'react';
import './Login.css';
import { Visibility, VisibilityOff, Person2 } from '@mui/icons-material';

export default function Login() {
	function handleConnexion(event: FormEvent<HTMLFormElement>): void {
		throw new Error('Function not implemented.');
	}
	const [showPassword, setShowPassword] = useState(false);

	const handleClickShowPassword = () => setShowPassword((show) => !show);

	const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
		event.preventDefault();
	};
	return (
		<>
			<Grid container marginTop={3} justifyContent={'center'}>
				<form onSubmit={handleConnexion} className="loginFormInput">
					<Grid item xs={12} marginY={3}>
						<TextField
							id="username"
							label="Username"
							type="text"
							variant="outlined"
							required
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
							Connexion
						</Button>
					</Grid>
				</form>
			</Grid>
		</>
	);
}
