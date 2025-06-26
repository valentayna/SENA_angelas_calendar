const list = document.querySelectorAll(".list");

function activelink() {
    list.forEach((item) => item.classList.remove("active"));
    this.classList.add("active");
}

list.forEach((item) =>
    item.addEventListener("click", activelink)
);


// Navegación activa
list.forEach(item =>
    item.addEventListener("click", function () {
        list.forEach(i => i.classList.remove("active"));
        this.classList.add("active");
    })
);

// Carrusel infinito correcto
const slider = document.querySelector('.slider');
const originalSlides = Array.from(slider.querySelectorAll('img'));
const slideCount = originalSlides.length;
const slideWidth = originalSlides[0].clientWidth;

// 1. Clonar imágenes y añadirlas al final
originalSlides.forEach(slide => {
    const clone = slide.cloneNode(true);
    slider.appendChild(clone);
});

// 2. Crear nuevo array con todos los slides (original + clones)
const allSlides = Array.from(slider.querySelectorAll('img'));
let currentIndex = 0;

function autoScroll() {
    currentIndex++;
    slider.scrollTo({
        left: currentIndex * slideWidth,
        behavior: 'smooth'
    });

    // 3. Si llegamos al final de los clones, nos teletransportamos a la imagen equivalente del original
    if (currentIndex >= slideCount * 2 - 1) {
        // Esperamos que termine el scroll animado
        setTimeout(() => {
            // 🧠 La imagen equivalente en los originales es: currentIndex % slideCount
            currentIndex = currentIndex % slideCount;
            slider.scrollTo({
                left: currentIndex * slideWidth,
                // sin animación visible
            });
        }, 500); // tiempo de la animación del scroll
    }
}

// Ejecutar cada 3 segundos
setInterval(autoScroll, 3000);
