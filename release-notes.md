# Pain (t) - Release notes

GitHub: https://github.com/sgronwold-CS250/pain-t-

## v0.5.0 (2024-09-29)

### New features

- UI cleanup
- Save to png, bmp, jpg (You can check the image properties, .bmp, .jpg, .jpeg actually save with that encoding)
  - Warning on data loss when saving to jpg
- Autosaves to autosave.png (in the default directory)
- Webserver on localhost:42069 serves the autosave image

### Known issues

- For some reason, canvas must be (even number) by (even number) otherwise live draw clobbers everything else

### Expected next version

- All features assigned in Sprint 6

## v0.4.0 (2024-09-20)

### New features

- Added multiple tabs
- Added "clear canvas" button
- Added regular polygons
- Can copy/cut/paste a portion of the image with Ctrl+C, Ctrl+X, Ctrl+V

### Known issues

- For some reason, canvas must be (even number) by (even number) otherwise live draw clobbers everything else

### Expected next version

- UI improvements

## v0.3.0 (2024-09-16)

### New features

- Implemented keyboard shortcuts
- Canvas automatically scales when you resize the window
- Added eyedropper
- Added pencil
- Added smart saving

### Known issues

- Dialog boxes do not do error-checking

### Expected

- Error handling on dialog boxes

## v0.2.0 (2024-09-06)

### New features

- Saves images to png, bmp, and jpeg file formats ("save as", and give your file the appropriate file extension. png, bmp, jpg, and jpeg save to the respective file format)
- Added the help window
- Added a line draw tool (customizable position, thickness, and color)

### Known issues

- When the user opens an invalid file an error is thrown

### Expected next version

- Error handling for the above errors


## v0.0.1 (2024-08-30)

### New features

- Can load an image
- Can save an image

### Known issues

N/A

### Expected next version

- Throw an error message if you open a non-image file.
- Canvas scaling to window size
- Drawing a line (and the user can determine the thickness and position of the line)