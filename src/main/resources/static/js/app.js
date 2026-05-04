let carrito = [];

function agregarDesdeBoton(btn) {

    const key = btn.getAttribute("data-key");
    const nombreBase = btn.getAttribute("data-nombre");
    const precio = parseFloat(btn.getAttribute("data-precio"));
    const forma = btn.getAttribute("data-forma");
    const shiny = btn.getAttribute("data-shiny") === "true";
    const imagen = btn.getAttribute("data-imagen");

    let nombre = nombreBase;

    if (forma !== "NORMAL") {
        nombre += " " + forma;
    }

    if (shiny) {
        nombre += " ✨";
    }

    let existente = carrito.find(p => p.key === key);

    if (existente) {
        existente.cantidad++;
    } else {
        carrito.push({
            key: key,
            nombre: nombre,
            precio: precio,
            cantidad: 1,
            imagen: imagen
        });
    }

    renderCarrito();
}

function eliminarDelCarrito(index) {
    carrito.splice(index, 1);
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
                    <img src="/images/${item.imagen}" width="40">
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
    `;
}