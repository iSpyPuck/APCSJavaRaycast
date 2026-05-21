# used ChatGPT to write this file properly
pkill -f "Xvfb :99" 2>/dev/null
pkill -f "x11vnc" 2>/dev/null
pkill -f "websockify" 2>/dev/null
sleep 0.5

Xvfb :99 -screen 0 1280x720x24 &
XVFB_PID=$!
export DISPLAY=:99
sleep 1

x11vnc -display :99 -nopw -localhost -xkb -forever -shared -quiet &
VNC_PID=$!

websockify --web /usr/share/novnc 6080 localhost:5900 &
NOVNC_PID=$!
sleep 1

URL="http://localhost:6080/vnc_auto.html?host=localhost&port=6080&autoconnect=true&resize=scale"
echo "Opening game at: $URL"
"${BROWSER:-xdg-open}" "$URL" 2>/dev/null &

mkdir -p out
javac -d out src/*.java && echo "Compiled successfully." && java -cp out Main

kill $NOVNC_PID $VNC_PID $XVFB_PID 2>/dev/null
