# emotrace
A Java Spring web app providing emotion analytics for online video content

Check it out at [emotrace-x1.appspot.com](https://emotrace-x1.appspot.com)

## Setup

**Import the emotrace repository folder into [IntelliJ 2016.3](https://www.jetbrains.com/idea/download/previous.html)**

**Install [Google Cloud SDK 137.0.1](https://console.cloud.google.com/storage/browser/cloud-sdk-release/)**

## Testing Locally

1. **Set current project**
    
    `gcloud config set project emotrace-x1`

2. **Start GAE Datastore Emulator**

    `gcloud beta emulators datastore start`

3. **Set GAE Datastore Emulator Environment Variables (in another terminal)**

    `$(gcloud beta emulators datastore env-init)`

4. **Select and run the intelliJ `GAE Local Dev` configuration**

5. **Open `http://localhost:8080` in your browser**

### Development Console

`http://localhost:8000`

## Deploying Remotely

**Select and run the `GAE Remote Deploy` configuration**

### Updating Remote Indexes

1. Modify `web/WEB-INF/datastore-indexes.xml`

2. Update Indexes remotely

    `./appengine-java-sdk-1.9.46/bin/appcfg.sh update_indexes emotrace/web`

## Dev Guide

1. **Pull/Sync master for latest code**

2. **Create new feature branch from master**

3. **Code out the feature on the new feature branch, test locally**

4. **When finished, pull/sync master again**

5. **Update feature branch from master, resolve conflicts**

5. **Merge feature branch into master locally**

6. **Push/Sync master**

## Helpful gcloud commands

Set current project

`gcloud config set project PROJECT_NAME`

List current project

`gcloud config list`

Create GAE app

`gcloud app create`

Clear local datastore

`rm ~/.config/gcloud/emulators/datastore/WEB-INF/appengine-generated/local_db.bin`
