import { Grid, Stack, Switch, Typography } from '@mui/material';
import { useState } from 'react';
import Register from '../../components/Register/Register';
import Login from '../../components/Login/Login';
import scale from '../../../public/scale-icon.png';
//import css
import './Landpage.css';
export default function Landpage() {
	const [isFirstConnection, setIsFirstConnection] = useState<boolean>(true);

	return (
		<>
			<Grid container>
				<Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
					<div className="titleLandpage">
						<h1>Open Weight Tracker</h1>
					</div>
				</Grid>
				<Grid
					item
					xs={12}
					sm={12}
					md={12}
					lg={12}
					xl={12}
					display={'flex'}
					justifyContent={'center'}
				>
					<div className="scaleIcon">
						<span>FREE</span>
						<img src={scale} alt="OWT" />
						<span>OPEN SOURCE</span>
					</div>
				</Grid>
				<Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
					<div className="descriptionLandpage">
						<p>
							Open Weight Tracker is an application that allows you to track your
							weight. It's free and opensource.
							<br />
							Is it your first time here ?
						</p>
					</div>
				</Grid>
				<Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
					<Stack
						direction="row"
						spacing={1}
						justifyContent={'center'}
						alignItems="center"
					>
						<Typography>Login</Typography>
						<Switch
							size="medium"
							inputProps={{ 'aria-label': 'ant design' }}
							onChange={(e) => setIsFirstConnection(e.target.checked)}
						/>
						<Typography>Register</Typography>
					</Stack>
				</Grid>
				<Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
					{isFirstConnection ? <Register /> : <Login />}
				</Grid>
			</Grid>
		</>
	);
}
