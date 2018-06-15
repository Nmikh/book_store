function fillBooksByAuthors() {
    let newList = document.getElementById("bookAuthorList");
    const booksJson = Cookies.get("allBooksByAuthors");
    const books = JSON.parse(booksJson);

    for (let i = 0; i < books.length; i++) {
        console.log("iterationNumber: " + i);
        let newCard = document.createElement("div");
        newCard.setAttribute("id", "BookCard" + i);
        newCard.classList.add("listCard", "zoom");
        newCard.style.color = "#F6AE2D";
        let bookIdAnchor = "bookIdAnchor" + +i;

        if (books[i]["price"] != -1.0) {
            newCard.innerHTML =
                "<div>" +
                "<p id='" + bookIdAnchor + "' style='display: none'>"
                + books[i]["id"] + "</p>" +
                "<p>" + '<br>' + books[i]["name"] + "</p>" +
                "</div>";

            newList.addEventListener("click", new function () {
                newCard.onclick = function () {
                    const books = document.getElementById(
                        bookIdAnchor).innerHTML;

                    document.getElementById("idField").value = books;
                    document.getElementById("typeField").value = "show";

                    let form = document.getElementById("hidden-book-form");
                    form.submit();
                }
            });

        } else {
            newCard.innerHTML =
                "<div>" +
                "<p id='" + bookIdAnchor + "' style='display: none'>"
                + books[i]["id"] + "</p>" +
                "<p>Author: </p>" +
                "<p>Name: " + '<br>' + books[i]["name"] + "</p>" +
                "<p>Surname:" + "<br>" + books[i]["description"] + "</p>" +
                "</div>";


        }
        newList.appendChild(newCard);
    }
}

function fillBooksByGenres() {
    let newList = document.getElementById("bookGenresList");
    const booksJson = Cookies.get("allBooksByGenres");
    const books = JSON.parse(booksJson);

    for (let i = 0; i < books.length; i++) {
        console.log("iterationNumber: " + i);
        let newCard = document.createElement("div");
        newCard.setAttribute("id", "BookCard" + i);
        newCard.classList.add("listCard", "zoom");
        newCard.style.color = "#F6AE2D";
        let bookIdAnchor = "bookIdAnchor" + +i;

        if (books[i]["price"] != -1.0) {
            newCard.innerHTML =
                "<div>" +
                "<p id='" + bookIdAnchor + "' style='display: none'>"
                + books[i]["id"] + "</p>" +
                "<p>" + '<br>' + books[i]["name"] + "</p>" +
                "</div>";

            newList.addEventListener("click", new function () {
                newCard.onclick = function () {
                    const books = document.getElementById(
                        bookIdAnchor).innerHTML;

                    document.getElementById("idField").value = books;
                    document.getElementById("typeField").value = "show";

                    let form = document.getElementById("hidden-book-form");
                    form.submit();
                }
            });

        } else {
            newCard.innerHTML =
                "<div>" +
                "<p id='" + bookIdAnchor + "' style='display: none'>"
                + books[i]["id"] + "</p>" +
                "<p>Genre: </p>" +
                "<p>" + books[i]["name"] + "</p>" +
                "</div>";


        }
        newList.appendChild(newCard);
    }
}