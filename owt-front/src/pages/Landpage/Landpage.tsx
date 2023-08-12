import { Grid, Stack, Switch, Typography } from '@mui/material';
import { useState } from 'react';
import Register from '../../components/Register/Register';
import Login from '../../components/Login/Login';
import scale from '../../assets/scale-icon.png';
//import css
import './Landpage.css';
export default function Landpage() {
	const [isFirstConnection, setIsFirstConnection] = useState<boolean>(false);
	return (
		<>
			<Grid container marginTop={6}>
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
						<span className="iconScale">FREE</span>
						<img src={scale} alt="OWT" />
						<span className="iconScale">OPEN SOURCE</span>
					</div>
				</Grid>
				<Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
					<div className="descriptionLandpage">
						<p>
							Open Weight Tracker is an application that allows you to track your
							weight. It's free and opensource.
							<br />
							<span className="firstTime">Is it your first time here ?</span>
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
						<Typography variant="h5">Login</Typography>
						<Switch
							size="medium"
							inputProps={{ 'aria-label': 'ant design' }}
							onChange={(e) => setIsFirstConnection(e.target.checked)}
						/>
						<Typography variant="h5">Register</Typography>
					</Stack>
				</Grid>
				<Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
					{isFirstConnection ? <Register /> : <Login />}
				</Grid>
			</Grid>
		</>
	);
}
