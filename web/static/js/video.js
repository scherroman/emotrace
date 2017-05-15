/**
 * Created by romanscher on 5/3/17.
 *
 * JS for controlling video pages
 */

$( document ).ready(function() {

    /* REUSABLE HANDLERS */

    /* NONREUSABLE HANDLERS */

    // Load emotion trace plots handler
    $.getScript("/static/js/emotrace-plots.js", function(){});
});

// ytplayer
var player = null;

// timer to collect data every <aggregate_ms>
var aggregate_timer = undefined;

// timer to push data to server every <server_ms>
var push_timer = undefined;

// boolean to keep track of whether to aggregate data or not
var isCollecting = false;

// How long to aggregate data for (in ms)
var aggregate_ms = 1000;

// How often to send data to server
var server_ms = 10000;

// number of times data was collected
var times_collected = 0;

// skeleton of json to collect data
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

// array of data to send over longer period of time
var data_arr = []

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
    $.getScript("/static/js/components/delete_card.js", function(){});

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
        detector.addEventListener("onWebcamConnectSuccess", onWebcamConnectSuccess(event));
        detector.addEventListener("onImageResultsSuccess", function (faces, image, timestamp) {
            if(isCollecting && faces[0] != null){
                //aggregate data
                times_collected++;
                aggregate['joy'] += faces[0].emotions.joy;
                aggregate['sadness'] += faces[0].emotions.sadness;
                aggregate['disgust'] += faces[0].emotions.disgust;
                aggregate['contempt'] += faces[0].emotions.contempt;
                aggregate['anger'] += faces[0].emotions.anger;
                aggregate['fear'] += faces[0].emotions.fear;
                aggregate['surprise'] += faces[0].emotions.surprise;
                aggregate['valence'] += faces[0].emotions.valence;
                aggregate['engagement'] += faces[0].emotions.engagement;
            }

        });
        onStart();
    }

    // 5. The API calls this function when the player's state changes.
    //    YT.PlayerState.PLAYING indicates when a video starts playing (state=1)
    function onPlayerStateChange(event) {
        if (event.data == YT.PlayerState.PLAYING) {
            isCollecting = true;
            aggregate_timer.resume();
            push_timer.resume()
        }
        else if (event.data == YT.PlayerState.PAUSED || event.data == YT.PlayerState.BUFFERING){
            isCollecting = false;
            aggregate_timer.pause();
            push_timer.pause();
        }
        else if (event.data == YT.PlayerState.ENDED){
            isCollecting = false;
            aggregate_timer.stop();
            push_timer.stop();
            pushData();
        }
    }



});

// Callback to notify when camera access is allowed
function onWebcamConnectSuccess(event) {
    console.log("I was able to connect to the camera successfully.");
    event.target.playVideo();
    isCollecting = true;
    aggregate_timer = new IntervalTimer(interval, aggregate_ms);
    push_timer = new IntervalTimer(pushData, server_ms);
}

// function that makes ajax call to server and resets data aggregation every <aggregate_ms>
function interval(){
    for(var emotion in aggregate){
        if(times_collected != 0)
            aggregate[emotion] = aggregate[emotion]/times_collected;
    }
    times_collected = 0;
    if(player === null)
        return;
    aggregate['timestamp'] = Math.floor(player.getCurrentTime());
    // console.log(JSON.stringify(aggregate));
    data_arr.push(aggregate);
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

// ajax call to send data to server every <server_ms>
function pushData(){
    console.log("Sending emotion data...");
    var data_to_send = {
        video_id: $('#player').data('video-id'),
        aggregates: data_arr
    };
    $.ajax({
        type: "POST",
        url: "/video/forms/store_emotion_data",
        // The key needs to match your method's input parameter (case-sensitive).
        data: JSON.stringify(data_to_send),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            console.log("Emotion data sent successfully.");
            data_arr = [];
        },
        failure: function(errMsg) {
            console.log(errMsg)
            alert(errMsg);
        }
    });
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

    this.stop = function() {
        window.clearInterval(timerId);
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


