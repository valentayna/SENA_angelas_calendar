window.addEventListener('resize', ajustarAltura);
window.addEventListener('DOMContentLoaded', ajustarAltura);

function ajustarAltura() {
    const elementos = document.querySelectorAll('.calendar_day');
    elementos.forEach(el => {
        el.style.height = `${el.offsetWidth}px`;
    });
}



const calendarDate = document.getElementById("calendar-date");
const daysContainer = document.querySelector(".calendar_days");
const prevBtn = document.querySelector(".calendar_button--previous");
const nextBtn = document.querySelector(".calendar_button--next");

const meses = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
];

let fechaActual = new Date();
// Extrae solo la parte YYYY-MM-DD de una fecha tipo "2025-08-05T00:00:00"
function normalizarFecha(fechaIso) {
    return fechaIso.split("T")[0];
}

// Convertir todas las fechas de las citas a formato YYYY-MM-DD
const fechasCitasNormales = fechasCitas.map(f => normalizarFecha(f));

function renderizarCalendario(fecha) {
    const año = fecha.getFullYear();
    const mes = fecha.getMonth();

    calendarDate.textContent = `${meses[mes]} ${año}`;

    const primerDia = new Date(año, mes, 1).getDay();
    const diasEnMes = new Date(año, mes + 1, 0).getDate();

    const primerDiaAjustado = (primerDia + 6) % 7;

    daysContainer.innerHTML = "";

    for (let i = 0; i < primerDiaAjustado; i++) {
        const diaVacio = document.createElement("li");
        diaVacio.classList.add("calendar_day", "empty");
        daysContainer.appendChild(diaVacio);
    }

    for (let dia = 1; dia <= diasEnMes; dia++) {
        const diaElemento = document.createElement("li");
        diaElemento.classList.add("calendar_day");
        diaElemento.setAttribute("data-day", dia);

        // 🟡 Nuevo: construir la fecha completa para compararla
        const diaConCeros = String(dia).padStart(2, '0');
        const mesConCeros = String(mes + 1).padStart(2, '0');
        const fechaCompleta = `${año}-${mesConCeros}-${diaConCeros}`;
        diaElemento.dataset.date = fechaCompleta; // Muy importante

        diaElemento.innerHTML = `
            <div class="day_info"><h5>${dia}</h5></div>
        `;

        // 🔎 Ver si la fecha está en citas
        if (fechasCitasNormales.includes(fechaCompleta)) {
            const hoy = new Date();
            const fechaCita = new Date(fechaCompleta);

            if (fechaCita < hoy.setHours(0, 0, 0, 0)) {
                // Cita pasada
                diaElemento.style.backgroundColor = "#86a560";

            } else {
                // Cita futura
                diaElemento.style.backgroundColor = "#f5bc00";
            }
            diaElemento.addEventListener("click", () => {
                const cita = citas.find(c => normalizarFecha(c.fecha) === fechaCompleta);
                if (cita) {
                    mostrarModalCita(cita);
                }
            });
        }

        daysContainer.appendChild(diaElemento);
        ajustarAltura();
    }
}

function mostrarModalCita(cita) {
    const modal = document.getElementById("modal-cita");
    document.getElementById("detalle-fecha").textContent = cita.fecha;
    document.getElementById("detalle-hora").textContent = `${cita.hora_inicio} - ${cita.hora_fin}`;
    document.getElementById("detalle-precio").textContent = cita.precio_total;
    modal.style.display = "block";
}

// Cerrar modal al hacer clic en la X
document.querySelector(".cerrar").onclick = function () {
    document.getElementById("modal-cita").style.display = "none";
};

// Cerrar modal al hacer clic fuera del modal
window.onclick = function (event) {
    const modal = document.getElementById("modal-cita");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}


// Botones para cambiar mes
prevBtn.addEventListener("click", () => {
    fechaActual.setMonth(fechaActual.getMonth() - 1);
    renderizarCalendario(fechaActual);
});

nextBtn.addEventListener("click", () => {
    fechaActual.setMonth(fechaActual.getMonth() + 1);
    renderizarCalendario(fechaActual);
});

// Iniciar al cargar la página
renderizarCalendario(fechaActual);
