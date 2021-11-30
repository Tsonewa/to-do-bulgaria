const tripId = document.getElementById('tripId').value;

const reviewsContainer =
    document.getElementById("comments-list");

const reviewsArray = [];

const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

const commentForm = document.getElementById('commentForm')
commentForm.addEventListener("submit", handleReviewSubmit)

async function handleReviewSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;
    const formData = new FormData(form);

    try {
        const responseData = await postFormDataAsJson({url, formData});

        reviewsContainer.insertAdjacentHTML("afterbegin", asReview(responseData));

        form.reset();
    } catch (error) {
        console.log(error)
    }
}

async function postFormDataAsJson({url, formData}) {

    const plainFormData = Object.fromEntries(formData.entries());
    const formDataAsJSONString = JSON.stringify(plainFormData);

    const fetchOptions = {
        method: "POST",
        headers: {
            [csrfHeaderName] : csrfHeaderValue,
            "Content-Type" : "application/json",
            "Accept" :"application/json"
        },
        body: formDataAsJSONString
    }

    const response = await fetch(url, fetchOptions);

    if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
    }

    return response.json();
}

    const displayReviews = (reviews) => {
    reviewsContainer.innerHTML = reviews.map((r) => {
        return asReview(r)
    }
    ).join('');
}

    function asReview(r){
    let li = document.createElement('li');
    let commantMainLevel = document.createElement('div');
    commantMainLevel.className = "comment-main-level";

    let avatarDiv = document.createElement('div');
    avatarDiv.className = "comment-avatar";
    let avatar = document.createElement('img');
    avatar.src = `${r.profilePictureUrl}`;
    avatarDiv.append(avatar);

    let commentBox = document.createElement('div');
    commentBox.className = "comment-box";

    let commantHeadDiv = document.createElement('div');
    commantHeadDiv.className = "comment-head";
    let h6 = document.createElement('h6');
        h6.className = "comment-name by-author";
        h6.innerText = `${r.user}`
        let span = document.createElement('span');
        span.innerText = `${r.createdOn}`;

        commantHeadDiv.append(h6);
        commantHeadDiv.append(span);

        let commentContentDiv = document.createElement('div');
        commentContentDiv.className = "comment-content";
        commentContentDiv.textContent = `${r.message}`

        commentBox.append(commantHeadDiv);
        commentBox.append(commentContentDiv);

        commantMainLevel.append(avatarDiv);
        commantMainLevel.append(commentBox);

        li.append(commantMainLevel);

        return li.innerHTML;
}



fetch(`http://localhost:8080/api/${tripId}/reviews`)
    .then(response => response.json())
    .then(data => {
        for (let review of data){
            reviewsArray.push(review);
        }
        displayReviews(reviewsArray);
    });
