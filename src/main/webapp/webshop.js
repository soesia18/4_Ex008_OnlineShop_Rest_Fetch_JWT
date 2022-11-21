let _jwt = '';
let _id = '';

function loadProducts() {
    let table = document.getElementById("productsTable");

    fetch('./api/products')
        .then(result => {
            result.json().then(data => {
                console.log(data);
                let str = '';
                let i = 1;
                data.forEach(product => {
                    str += '<tr>';
                    str += '<td>' + product.name + '</td>';
                    str += '<td><img width="50px" height="50px" src="' + product.imageUrl + '" alt=""></td>';
                    str += '<td><button onclick="showDetail(\'' + product.name + '\',\'' + product.description + '\')" class="btn btn-primary">Details</button></td>';
                    str += '<td><button onclick="addToBasket(' + i++ + ')"><img width="50px" height="50px" src="https://th.bing.com/th/id/R.8e3b15a9403fe61de8bb80ee000091e3?rik=cH71UO8fOSyq0g&riu=http%3a%2f%2fcdn.onlinewebfonts.com%2fsvg%2fimg_561093.png&ehk=paYU96WNGFlg495gi3OH7Wd%2fh3Jy%2fHnCMB0ZDHVdnAA%3d&risl=&pid=ImgRaw&r=0" alt=""></button></td>';
                    str += '</tr>';
                });

                document.getElementById('productsTable').innerHTML = str;

            })
        })
}

function login(username, password) {
    fetch('./api/login', {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({"username": username, "password": password})
    })
        .then(result => {

            if (result.status !== 200) {
                document.getElementById('lbUser').innerHTM = '';
                alert(result.statusText + " " + result.status);
                return;
            }

            _jwt = result.headers.get("Authorization");

            console.log(_jwt);

            document.getElementById('lbUser').innerHTML = username;
            createBasket();

        })
}

function createBasket() {
    fetch('./api/basket', {
        headers: {"Authorization": _jwt}
    })
        .then(result => {
            console.log(result.headers.get("Location"));

            _id = result.headers.get("Location").split("/").pop();

            loadBasket();
        })
}

function addToBasket(prodId) {
    if (_id === '') {
        alert("You need first to login");
        return;
    }
    fetch('./api/basket/' + _id, {
        method: "POST",
        headers: {"Authorization": _jwt},
        body: prodId
    })
        .then(result => {
            result.json().then(data => {
                console.log(data);
                loadBasket()
            })
        })
}

function loadBasket() {
    if (_id === '') {
        alert("You need first to login");
        return;
    }
    fetch('./api/basket/' + _id, {
        headers: {"Authorization": _jwt}
    })
        .then(result => {
            result.json().then(data => {
                console.log(data);
                let str = '';
                let sum = 0;

                Object.keys(data.productMap).forEach(index => {
                    sum += data.productMap[index].key.price * data.productMap[index].value;
                    str += '<tr>';
                    str += '<td>' + data.productMap[index].value + '</td>';
                    str += '<td>' + data.productMap[index].key.name + '</td>';
                    str += '<td>' + data.productMap[index].key.price + ',-</td>';
                    str += '<td><button onclick="deleteProduct(' + data.productMap[index].key.id + ')"><img width="50px" height="50px" src="https://th.bing.com/th/id/R.28c8fb93b3748e16c0878c0e71efb2f3?rik=XzuIfXjTGg6DYw&pid=ImgRaw&r=0"></button></td>';
                    str += '</tr>';
                })

                str += '<tr>';
                str += '</tr>';

                str += '<tr>';
                str += '<td>Gesamt</td>';
                str += '<td>' + sum + ',-</td>';
                str += '</tr>';

                document.getElementById('basket').innerHTML = str;
            })
        })
}

function deleteProduct(prodId) {
    if (_id === '') {
        alert("You need first to login");
        return;
    }
    fetch('./api/basket/' + _id + '/' + prodId, {
        method: "DELETE",
        headers: {"Authorization": _jwt}
    })
        .then(() => {
            loadBasket();
        })
}

function showDetail(name, description) {
    let str = 'Details of the ' + name + '. ' + description;

    if (str.trim() === document.getElementById('details').innerText.trim()) {
        document.getElementById('details').innerHTML = '';
    } else {
        document.getElementById('details').innerHTML = str;
    }
}