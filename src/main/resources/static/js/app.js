let carrito = JSON.parse(localStorage.getItem("carrito")) || [];

function guardarCarrito() {
    localStorage.setItem("carrito", JSON.stringify(carrito));
}

function agregarDesdeBoton(btn) {
    const nombre = btn.getAttribute("data-nombre");
    const precio = parseFloat(btn.getAttribute("data-precio"));

    let existente = carrito.find(p => p.nombre === nombre);

    if (existente) {
        existente.cantidad++;
    } else {
        carrito.push({
            nombre,
            precio,
            cantidad: 1,
            imagen: nombre.toLowerCase() + ".png"
        });
    }

    guardarCarrito();
    renderCarrito();
}

function eliminarDelCarrito(index) {
    carrito.splice(index, 1);
    guardarCarrito();
    renderCarrito();
}

function renderCarrito() {

    const lista = document.getElementById("carrito-lista");
    lista.innerHTML = "";

    if (carrito.length === 0) {
        lista.innerHTML = "<p>No hay productos en el carrito</p>";
        return;
    }

    let total = 0;

    carrito.forEach((item, index) => {

        let subtotal = item.precio * item.cantidad;
        total += subtotal;

        const div = document.createElement("div");
        div.className = "card mb-2 p-2";

        div.innerHTML = `
            <div class="d-flex align-items-center justify-content-between">
                <div class="d-flex align-items-center gap-2">
                    <img src="/images/${item.imagen}" width="40" height="40">
                    <div>
                        <div>${item.nombre}</div>
                        <small>$${item.precio} x ${item.cantidad}</small>
                    </div>
                </div>
                <button class="btn btn-danger btn-sm" onclick="eliminarDelCarrito(${index})">X</button>
            </div>
        `;

        lista.appendChild(div);
    });

    lista.innerHTML += `
        <hr>
        <strong>Total: $${total}</strong>
        <br><br>
        <button class="btn btn-success w-100" onclick="enviarWhats()">Comprar todo</button>
    `;
}

function enviarWhats() {

    let mensaje = "Hola, quiero comprar:%0A";

    carrito.forEach(p => {
        mensaje += `- ${p.nombre} x${p.cantidad}%0A`;
    });

    const telefono = "5215528438110";
    window.open(`https://wa.me/${telefono}?text=${mensaje}`);
}

renderCarrito();