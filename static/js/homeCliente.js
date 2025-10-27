// Datos de ejemplo (reemplaza con tus datos reales)
const citas = [
    { fecha: '2025-10-28T00:00:00', hora_inicio: '10:00', hora_fin: '11:00', precio_total: '50000' },
    { fecha: '2025-11-15T00:00:00', hora_inicio: '14:00', hora_fin: '15:30', precio_total: '75000' }
];

const meses = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
];

let fechaActual = new Date();

function normalizarFecha(fechaIso) {
    return fechaIso.split("T")[0];
}

const fechasCitas = citas.map(c => normalizarFecha(c.fecha));

function renderizarCalendario(fecha) {
    const año = fecha.getFullYear();
    const mes = fecha.getMonth();

    document.getElementById('calendarTitle').textContent = `${meses[mes]} ${año}`;

    const primerDia = new Date(año, mes, 1).getDay();
    const diasEnMes = new Date(año, mes + 1, 0).getDate();
    const primerDiaAjustado = (primerDia + 6) % 7;

    const daysContainer = document.getElementById('calendarDays');
    daysContainer.innerHTML = '';

    // Días vacíos
    for (let i = 0; i < primerDiaAjustado; i++) {
        const div = document.createElement('div');
        div.className = 'calendar-day empty';
        daysContainer.appendChild(div);
    }

    // Días del mes
    for (let dia = 1; dia <= diasEnMes; dia++) {
        const div = document.createElement('div');
        div.className = 'calendar-day';
        div.textContent = dia;

        const diaConCeros = String(dia).padStart(2, '0');
        const mesConCeros = String(mes + 1).padStart(2, '0');
        const fechaCompleta = `${año}-${mesConCeros}-${diaConCeros}`;

        if (fechasCitas.includes(fechaCompleta)) {
            const hoy = new Date();
            const fechaCita = new Date(fechaCompleta);

            if (fechaCita < hoy.setHours(0, 0, 0, 0)) {
                div.classList.add('past-appointment');
            } else {
                div.classList.add('has-appointment');
            }

            div.addEventListener('click', () => {
                const cita = citas.find(c => normalizarFecha(c.fecha) === fechaCompleta);
                if (cita) {
                    document.getElementById('detalleFecha').textContent = cita.fecha.split('T')[0];
                    document.getElementById('detalleHora').textContent =
                        `${cita.hora_inicio} - ${cita.hora_fin}`;
                    document.getElementById('detallePrecio').textContent =
                        `$${Number(cita.precio_total).toLocaleString()}`;

                    const modal = new bootstrap.Modal(document.getElementById('citaModal'));
                    modal.show();
                }
            });
        }

        daysContainer.appendChild(div);
    }
}

document.getElementById('prevMonth').addEventListener('click', () => {
    fechaActual.setMonth(fechaActual.getMonth() - 1);
    renderizarCalendario(fechaActual);
});

document.getElementById('nextMonth').addEventListener('click', () => {
    fechaActual.setMonth(fechaActual.getMonth() + 1);
    renderizarCalendario(fechaActual);
});

renderizarCalendario(fechaActual);
