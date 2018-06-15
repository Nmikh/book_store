function fillBookData() {
    const bookJson = Cookies.get("book");

    const book = JSON.parse(bookJson);

    console.log(book);

    document.getElementById("bookNameField")
        .innerText += book["name"] + "\n";
    document.getElementById("bookDescriptionField")
        .innerText += book["description"] + "\n";
    document.getElementById("bookPriceField")
        .innerText += book["price"];
}

function fillBookByAuthors() {
    let newList = document.getElementById("bookAuthorList");
    const authJson = Cookies.get("bookAuth");
    const auth = JSON.parse(authJson);

    for (let i = 0; i < auth.length; i++) {
        console.log("iterationNumber: " + i);
        let newCard = document.createElement("div");
        newCard.setAttribute("id", "BookCard" + i);
        newCard.classList.add("listCard", "zoom");
        newCard.style.color = "#F6AE2D";
        let bookIdAnchor = "bookIdAnchor" + +i;

        newCard.innerHTML =
            "<div>" +
            "<p id='" + bookIdAnchor + "' style='display: none'>"
            + auth[i]["id"] + "</p>" +
            "<p>Name: " + '<br>' + auth[i]["name"] + "</p>" +
            "<p>Surname:" + "<br>" + auth[i]["surname"] + "</p>" +
            "</div>";


        newList.appendChild(newCard);
    }
}

function fillBookByGenres() {
    let newList = document.getElementById("bookGenresList");
    const genresJson = Cookies.get("bookGenres");
    const genres = JSON.parse(genresJson);

    for (let i = 0; i < genres.length; i++) {
        console.log("iterationNumber: " + i);
        let newCard = document.createElement("div");
        newCard.setAttribute("id", "BookCard" + i);
        newCard.classList.add("listCard", "zoom");
        newCard.style.color = "#F6AE2D";
        let bookIdAnchor = "bookIdAnchor" + +i;
        newCard.innerHTML =
            "<div>" +
            "<p id='" + bookIdAnchor + "' style='display: none'>"
            + genres[i]["id"] + "</p>" +
            "<p>" + genres[i]["name"] + "</p>" +
            "</div>";

        newList.appendChild(newCard);
    }
}