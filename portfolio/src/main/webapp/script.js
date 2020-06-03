// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
window.onload = async () => {
    getComments();
    attachCommentFormSubmitEvent();
}

const getComments = async () => {
    const response = await fetch('/data');
    const comments = JSON.parse(await response.text());
    const paraList= comments.map((comment)=>{
        const paragraph = document.createElement("P");
        paragraph.innerText=comment;
        paragraph.className="comment"
        return paragraph;
    });
    const divList = paraList.map((para)=>{
        const div = document.createElement("DIV");
        div.appendChild(para);
        div.className="commentDiv py-2 px-4";
        return div;
    });
    const commentsContainer = document.getElementById("commentsContainer");
    commentsContainer.innerHTML='';
    divList.forEach((div)=>{
        commentsContainer.appendChild(div);
    });
    console.log(divList);
}


const addComment = async (event) => {
    event.preventDefault();
    await fetch('/data', {
        method: 'post',
        body: new URLSearchParams({ "commentText": document.getElementById('commentText').value })
    });

    getComments();
}

function attachCommentFormSubmitEvent() {
    document.getElementById("addCommentForm").addEventListener("submit", addComment);
}