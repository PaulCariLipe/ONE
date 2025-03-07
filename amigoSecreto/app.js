let lista = [];
let listaAmigos = document.getElementById('listaAmigos');
let resultado = document.getElementById('resultado');

function agregarAmigo() {
    let amigo = document.getElementById('amigo').value;
    if (amigo) {
        lista.push(amigo);
        mostrar();
        document.getElementById('amigo').value = "";
    } else {
        alert("Por favor, ingresa un nombre válido.");
    }
}

function mostrar() {
    listaAmigos.innerHTML = "";
    for (let i = 0; i < lista.length; i++) {
        let li = document.createElement("li");
        li.textContent = lista[i];
        listaAmigos.appendChild(li);
    }
}

function sortearAmigo() {
    if (lista.length > 0) {
        let aleatorio = Math.floor(Math.random() * lista.length);
        let amigoSorteado = lista[aleatorio];

        resultado.innerHTML = "";
        let li = document.createElement("li");
        li.textContent = `🎉 Amigo sorteado: ${amigoSorteado}`;
        resultado.appendChild(li);
    } else {
        resultado.innerHTML = "<li>No hay amigos para sortear.</li>";
    }
}


