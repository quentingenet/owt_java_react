import {
    Fab,
    Grid,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import ModeEditIcon from '@mui/icons-material/ModeEdit';

import { formatDate } from '../../utils/GlobalUtils';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import { useUserContext } from '../../contexts/UserContext';
import AddIcon from '@mui/icons-material/Add';
export default function Weights() {
    const userContext = useUserContext();

    function createData(
        idWeigth: number,
        dateWeight: string,
        weightValue: number,
        muscularMass: number,
        fatMass: number,
        bodyWater: number,
        boneMass: number
    ) {
        return {
            idWeigth,
            dateWeight,
            weightValue,
            muscularMass,
            fatMass,
            bodyWater,
            boneMass,
        };
    }

    const rows = [
        createData(1, formatDate(new Date()), 95.8, 1, 6.0, 24, 5),
        createData(1, formatDate(new Date()), 99, 1, 6.0, 24, 4.0),
        createData(1, formatDate(new Date()), 84, 1, 6.0, 24, 4.0),
        createData(1, formatDate(new Date()), 82, 1, 6.0, 24, 4.0),
    ];

    return (
        <>
            <Grid
                container
                marginTop={6}
                justifyContent={'center'}
                flexDirection={'column'}
            >
                <Typography variant='h2'>My weights</Typography>
            </Grid>
            <Grid container justifyContent={'center'}>
                <Grid
                    item
                    marginY={5}
                    xs={12}
                    md={4}
                    justifyContent={'center'}
                    flexDirection={'column'}
                >
                    <TableContainer component={Paper}>
                        <Table aria-label='simple table'>
                            <TableHead>
                                <TableRow>
                                    <TableCell align='center' width='1%'>
                                        <AddIcon sx={{ cursor: 'pointer' }} />
                                    </TableCell>
                                    <TableCell
                                        align='center'
                                        width='1%'
                                    ></TableCell>
                                    <TableCell
                                        align='center'
                                        width='1%'
                                    ></TableCell>
                                    <TableCell align='center' width='3%'>
                                        <span style={{ fontWeight: 'bold' }}>
                                            Id
                                        </span>
                                    </TableCell>
                                    <TableCell align='center' width='3%'>
                                        <span style={{ fontWeight: 'bold' }}>
                                            Date
                                        </span>
                                    </TableCell>
                                    <TableCell align='center' width='3%'>
                                        <span style={{ fontWeight: 'bold' }}>
                                            {userContext.isEuropeanUnitMeasure
                                                ? 'Kg'
                                                : 'Lbs'}
                                        </span>
                                    </TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {rows.map((row) => (
                                    <TableRow
                                        key={row.idWeigth}
                                        sx={{
                                            '&:last-child td, &:last-child th':
                                                {
                                                    border: 0,
                                                },
                                        }}
                                    >
                                        <TableCell align='center'>
                                            <RemoveRedEyeIcon
                                                sx={{ cursor: 'pointer' }}
                                            />
                                        </TableCell>
                                        <TableCell
                                            component='th'
                                            align='center'
                                            scope='row'
                                        >
                                            <ModeEditIcon
                                                sx={{ cursor: 'pointer' }}
                                            />
                                        </TableCell>
                                        <TableCell align='center'>
                                            <DeleteIcon
                                                sx={{ cursor: 'pointer' }}
                                            />
                                        </TableCell>
                                        <TableCell align='center'>
                                            {row.idWeigth}
                                        </TableCell>
                                        <TableCell align='center'>
                                            {row.dateWeight}
                                        </TableCell>
                                        <TableCell align='center'>
                                            {row.weightValue}
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Grid>
            </Grid>
        </>
    );
}
