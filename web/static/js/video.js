/**
 * Created by romanscher on 5/3/17.
 */

// ytplayer
var player = null;

// timer to collect data over <seconds>
var timer = undefined;

// How long to aggregate data for (in ms)
var seconds = 10;

var aggregate = {
    joy:0,
    sadness:0,
    disgust:0,
    contempt:0,
    anger:0,
    fear:0,
    surprise:0,
    valence:0,
    engagement:0,
    timestamp:0
};

/*
 Face detector configuration - If not specified, defaults to
 affdex.FaceDetectorMode.LARGE_FACES
 affdex.FaceDetectorMode.LARGE_FACES=Faces occupying large portions of the frame
 affdex.FaceDetectorMode.SMALL_FACES=Faces occupying small portions of the frame
 */
var faceMode = affdex.FaceDetectorMode.LARGE_FACES;

// Construct a CameraDetector and specify the image width / height and face detector mode.
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
    // var player;
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

        });
    }

    // 5. The API calls this function when the player's state changes.
    //    YT.PlayerState.PLAYING indicates when a video starts playing (state=1)
    function onPlayerStateChange(event) {
        if (event.data == YT.PlayerState.PLAYING) {
            timer.resume();
        }
        else if (event.data == YT.PlayerState.PAUSED || event.data == YT.PlayerState.BUFFERING){
            timer.pause()
        }
    }



});

// Callback to notify when camera access is allowed
function onWebcamConnectSuccess(event) {
    console.log("I was able to connect to the camera successfully.");
    event.target.playVideo();
    timer = new IntervalTimer(interval, seconds);
    // setInterval(interval, seconds);
}

// function that makes ajax call to server and resets data aggregation every <seconds>
function interval(){
    for(var emotion in aggregate){
        aggregate[emotion] = aggregate[emotion]/seconds;
    }
    var video_id = $('#player').data('video-id');
    // var ytplayer = document.getElementById('player_uid_' + video_id);
    if(player === null)
        return;
    aggregate['timestamp'] = player.getCurrentTime();
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
}

// timer function to allow for easier pause/resume of setInterval callback
function IntervalTimer(callback, interval) {
    var timerId, startTime, remaining = 0;
    var state = 0; //  0 = idle, 1 = running, 2 = paused, 3= resumed

    this.pause = function () {
        if (state != 1) return;

        remaining = interval - (new Date() - startTime);
        window.clearInterval(timerId);
        state = 2;
    };

    this.resume = function () {
        if (state != 2) return;

        state = 3;
        window.setTimeout(this.timeoutCallback, remaining);
    };

    this.timeoutCallback = function () {
        if (state != 3) return;

        callback();

        startTime = new Date();
        timerId = window.setInterval(callback, interval);
        state = 1;
    };

    startTime = new Date();
    timerId = window.setInterval(callback, interval);
    state = 1;
}

// function executes affectiva API when video starts playing.
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


