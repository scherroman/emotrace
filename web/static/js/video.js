/**
 * Created by romanscher on 5/3/17.
 */

// How long to aggregate data for (in ms)
var seconds = 1000;

var aggregate = {
    joy:0,
    sadness:0,
    disgust:0,
    contempt:0,
    anger:0,
    fear:0,
    surprise:0,
    valence:0,
    engagement:0
};

/*
 Face detector configuration - If not specified, defaults to
 affdex.FaceDetectorMode.LARGE_FACES
 affdex.FaceDetectorMode.LARGE_FACES=Faces occupying large portions of the frame
 affdex.FaceDetectorMode.SMALL_FACES=Faces occupying small portions of the frame
 */
var faceMode = affdex.FaceDetectorMode.LARGE_FACES;

//Construct a CameraDetector and specify the image width / height and face detector mode.
var detector = new affdex.CameraDetector(undefined, 0, 0, faceMode);

// only collect emotions
detector.detectAllEmotions();

$(document).ready(function() {

    // LOAD YOUTUBE VIDEO //

    // Get youtube video's id
    var video_id = $('#player').data('video-id');

    // 2. This code loads the IFrame Player API code asynchronously.
    var tag = document.createElement('script');

    tag.src = "https://www.youtube.com/iframe_api";
    var firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

    // 3. This function creates an <iframe> (and YouTube player)
    //    after the API code downloads.
    var player;
    window.onYouTubeIframeAPIReady =  function () {
        player = new YT.Player('player', {
            videoId: video_id,
            events: {
                'onReady': onPlayerReady,
                'onStateChange': onPlayerStateChange
            }
        });
    };

    // 4. The API will call this function when the video player is ready.
    function onPlayerReady(event) {
        detector.detectAllEmotions();
        onStart();
        detector.addEventListener("onWebcamConnectSuccess", onWebcamConnectSuccess(event));
        detector.addEventListener("onImageResultsSuccess", function (faces, image, timestamp) {
            //aggregate data
            aggregate['joy'] += faces[0].emotions.joy;
            aggregate['sadness'] += faces[0].emotions.sadness;
            aggregate['disgust'] += faces[0].emotions.disgust;
            aggregate['contempt'] += faces[0].emotions.contempt;
            aggregate['anger'] += faces[0].emotions.anger;
            aggregate['fear'] += faces[0].emotions.fear;
            aggregate['surprise'] += faces[0].emotions.surprise;
            aggregate['valence'] += faces[0].emotions.valence;
            aggregate['engagement'] += faces[0].emotions.engagement;


            // console.log("IMAGE RESULTS SUCCESS");
            // console.log("timestamp: " + timestamp.toFixed(2));
            // console.log("Emotions: " + JSON.stringify(faces[0].emotions.joy));
            // console.log("Emotions: " + JSON.stringify(faces[0].emotions, function(key, val) {
            //         return val.toFixed ? Number(val.toFixed(0)) : val;
            //     }));
        });
        // event.target.playVideo();
    }

    // 5. The API calls this function when the player's state changes.
    //    YT.PlayerState.PLAYING indicates when a video starts playing (state=1)
    function onPlayerStateChange(event) {
        if (event.data == YT.PlayerState.PLAYING) {}
    }



});

function onWebcamConnectSuccess(event) {
    console.log("I was able to connect to the camera successfully.");
    setInterval(function(){
        console.log(JSON.stringify(aggregate));
        // $.ajax({
        //     type: "POST",
        //     url: "/test",
        //     // The key needs to match your method's input parameter (case-sensitive).
        //     data: JSON.stringify(aggregate),
        //     contentType: "application/json; charset=utf-8",
        //     dataType: "json",
        //     success: function(data){
        //         debugger;
        //         console.log("sent data!");
        //
        //         // window.location.href = data.url;
        //     },
        //     failure: function(errMsg) {
        //         alert(errMsg);
        //     }
        // });
        aggregate = {
            joy:0,
            sadness:0,
            disgust:0,
            contempt:0,
            anger:0,
            fear:0,
            surprise:0,
            valence:0,
            engagement:0
        };
    }, seconds);
    event.target.playVideo();
}

//function executes affectiva API when video starts playing.
function onStart() {
    if (detector && !detector.isRunning) {
        detector.start();
    }
    detector._startCamera();
    console.log("Started camera");
}

// function onImageResultsSuccess(faces, image, timestamp) {
//     //aggregate data
//     aggregate['joy'] += faces[0].emotions.joy;
//     aggregate['sadness'] += faces[0].emotions.sadness;
//     aggregate['disgust'] += faces[0].emotions.disgust;
//     aggregate['contempt'] += faces[0].emotions.contempt;
//     aggregate['anger'] += faces[0].emotions.anger;
//     aggregate['fear'] += faces[0].emotions.fear;
//     aggregate['surprise'] += faces[0].emotions.surprise;
//     aggregate['valence'] += faces[0].emotions.valence;
//     aggregate['engagement'] += faces[0].emotions.engagement;
// }


