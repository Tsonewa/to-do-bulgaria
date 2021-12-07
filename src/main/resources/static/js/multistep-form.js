function home(){
    document.getElementById("input1-wrapper").style.display = "flex";
    document.getElementById("itinerary-div").style.display = "none";
    document.getElementById("input3-wrapper").style.display = "none";
}
home();

function secondFieldset() {
    document.getElementById("input1-wrapper").style.display = "none";
    document.getElementById("itinerary-div").style.display = "flex";
    document.getElementById("input3-wrapper").style.display = "none";
}
function lastFildset(){
    document.getElementById("input1-wrapper").style.display = "none";
    document.getElementById("itinerary-div").style.display = "none";
    document.getElementById("input3-wrapper").style.display = "flex";
}

function addAttraction(){
    let parent = document.getElementById('itinerary-div');
    let n = Number(parent.getElementsByTagName('fieldset').length) - 1;

    let input = document.createElement('input');
    input.name = "attractionsName";
    input.type = "text";
    input.placeholder = "Historical Museum";

    let fieldset = document.getElementsByClassName("input-div")[n];

    fieldset.appendChild(input);
}

function addDay(){
    let parent = document.getElementById('itinerary-div');
    let n = Number(parent.getElementsByTagName('fieldset').length) + 1;

    let fieldset = document.createElement('fieldset')
    let h2 = document.createElement('h2');
    let div = document.createElement('div');
    div.className = "input-div";

    h2.textContent = 'Ден ' + n;
    h2.className= "fs-title";
    let label = document.createElement('label');
    label.textContent = "Град";
    div.appendChild(h2);
    div.appendChild(label);

    let townInput = document.createElement('input');
    townInput.type = "text";
    townInput.name = "townName";
    townInput.placeholder = "София";
    div.appendChild(townInput);
    let labelBrekfast = document.createElement('label');
    labelBrekfast.textContent = "Закуска";

    let breakfastInput = document.createElement('input');
    breakfastInput.type = "text";
    breakfastInput.name = "breakfastPlace";
    breakfastInput.placeholder = "Мекица и Кафе";
    div.appendChild(labelBrekfast).appendChild(breakfastInput);

    let labelCoffee = document.createElement('label');
    labelCoffee.textContent = "Кафе";
    let coffeeInput = document.createElement('input');
    coffeeInput.type = "text";
    coffeeInput.name = "coffeePlace";
    coffeeInput.placeholder = "Кафе бар";
    div.appendChild(labelCoffee).appendChild(coffeeInput);

    let hotelCoffee = document.createElement('label');
    hotelCoffee.textContent = "Хотел";
    let hotelInput = document.createElement('input');
    hotelInput.type = "text";
    hotelInput.name = "hotel";
    hotelInput.placeholder = "Grand Hotel Sofia";
    div.appendChild(hotelCoffee).appendChild(hotelInput);

    let dinnerLabel = document.createElement('label');
    dinnerLabel.textContent = "Вечеря";
    let dinnerInput = document.createElement('input');
    dinnerInput.type = "text";
    dinnerInput.name = "dinnerPlace";
    dinnerInput.placeholder = "Синята къща";
    div.appendChild(dinnerLabel).appendChild(dinnerInput);

    let attractionsLabel = document.createElement('label');
    attractionsLabel.textContent = `Атракция`;
    let attractionsInput = document.createElement('input');
    attractionsInput.type = "text";
    attractionsInput.name = "attractionsName";
    attractionsInput.placeholder = "Национален дворец на културата";
    div.appendChild(attractionsLabel).appendChild(attractionsInput);

    // // let btn = document.createElement('button');
    // // btn.type = "button";
    // // btn.name = "add-attraction";
    // // btn.className="add-attraction-btn";
    // // btn.value="Add attraction"
    // // btn.textContent = "Add attraction";

    fieldset.appendChild(div);
    // // fieldset.appendChild(btn);
    parent.appendChild(fieldset);
}