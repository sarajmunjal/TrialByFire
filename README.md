### Firebase experimentation
Writes data to Firestore using the Python Admin SDK
Reads data written to Firestore on an Android app in a paginated way

#### Instructions to run
1. Python scripts
   - Add service account private key file with the name 'private_key.json' in the 'setup' directory
   - Install dependencies `pip3 install firebase-admin google-cloud-firestore`
   - Run script `python3 script.py`

2. Android app
   - Add google-services.json file in the root directory of the project: with your own Firebase configs.
   - Run `./gradlew clean :app:build`. This will build the APK for the app, which can be found in the `app/build/outputs` directory, and can be installed and run on your Android phone/emulator