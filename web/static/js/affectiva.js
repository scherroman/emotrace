/**
 * Created by esamudio on 4/7/17.
 */

/*
 SDK Needs to create video and canvas nodes in the DOM in order to function
 Here we are adding those nodes a predefined div.
 */
var divRoot = $("#affdex_elements")[0];

// The captured frame's width in pixels
var width = 640;

// The captured frame's height in pixels
var height = 480;

// How long to aggregate data for (in ms)
var seconds = 1000

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
var detector = new affdex.CameraDetector(divRoot, width, height, faceMode);

detector.detectAllEmotions();


detector.addEventListener("onInitializeSuccess", function() {
    console.log("INITIALIZE SUCCESS");
});
detector.addEventListener("onInitializeFailure", function() {
    console.log("INITIALIZE FAILURE");
});

detector.addEventListener("onResetSuccess", function() {
    console.log("RESET SUCCESS");
});
detector.addEventListener("onResetFailure", function() {
    console.log("RESET FAILURE");
});
detector.addEventListener("onStopSuccess", function() {
    console.log("STOP SUCCESS");
});
detector.addEventListener("onStopFailure", function() {
    console.log("STOP FAILURE");
});
detector.addEventListener("onWebcamConnectSuccess", function() {
    console.log("I was able to connect to the camera successfully.");
    setInterval(function(){
        // console.log(JSON.stringify(aggregate));
        $.ajax({
            type: "POST",
            url: "/test",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify(aggregate),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data){
                debugger;
                console.log("sent data!");

                // window.location.href = data.url;
            },
            failure: function(errMsg) {
                alert(errMsg);
            }
        });
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
});

detector.addEventListener("onWebcamConnectFailure", function() {
    console.log("I've failed to connect to the camera :(");
});

/*
 onImageResults success is called when a frame is processed successfully and receives 3 parameters:
 - Faces: Dictionary of faces in the frame keyed by the face id.
 For each face id, the values of detected emotions, expressions, appearane metrics
 and coordinates of the feature points
 - image: An imageData object containing the pixel values for the processed frame.
 - timestamp: The timestamp of the captured image in seconds.
 */
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

/*
 onImageResults success receives 3 parameters:
 - image: An imageData object containing the pixel values for the processed frame.
 - timestamp: An imageData object contain the pixel values for the processed frame.
 - err_detail: A string contains the encountered exception.
 */
detector.addEventListener("onImageResultsFailure", function (image, timestamp, err_detail) {
    console.log("IMAGE RESULTS FAILURE");
});

//function executes when Start button is pushed.
function onStart() {
    if (detector && !detector.isRunning) {
        detector.start();
    }
    detector._startCamera();
    console.log("Clicked the start button");
}

//function executes when the Stop button is pushed.
function onStop() {
    console.log("Clicked the stop button");
    if (detector && detector.isRunning) {
        detector.removeEventListener();
        detector.stop();
    }
};

//function executes when the Reset button is pushed.
function onReset() {
    console.log("Clicked the reset button");
    if (detector && detector.isRunning) {
        detector.reset();
    }
};
