/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openglsb;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glClearBufferfv;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Ch5SpinningCube {

    public static void main(String[] args) {
        Ch5SpinningCube app = new Ch5SpinningCube();
        app.run();
    }

    private long window;
    private int program;
    private int vao;
    private int buffer;
    private int mv_location;
    private int proj_location;
    //private float aspect;
    private final FloatBuffer proj_matrix = BufferUtils.createFloatBuffer(16);

    static float vertex_positions[]
            = {
                -0.25f, 0.25f, -0.25f,
                -0.25f, -0.25f, -0.25f,
                0.25f, -0.25f, -0.25f,
                0.25f, -0.25f, -0.25f,
                0.25f, 0.25f, -0.25f,
                -0.25f, 0.25f, -0.25f,
                0.25f, -0.25f, -0.25f,
                0.25f, -0.25f, 0.25f,
                0.25f, 0.25f, -0.25f,
                0.25f, -0.25f, 0.25f,
                0.25f, 0.25f, 0.25f,
                0.25f, 0.25f, -0.25f,
                0.25f, -0.25f, 0.25f,
                -0.25f, -0.25f, 0.25f,
                0.25f, 0.25f, 0.25f,
                -0.25f, -0.25f, 0.25f,
                -0.25f, 0.25f, 0.25f,
                0.25f, 0.25f, 0.25f,
                -0.25f, -0.25f, 0.25f,
                -0.25f, -0.25f, -0.25f,
                -0.25f, 0.25f, 0.25f,
                -0.25f, -0.25f, -0.25f,
                -0.25f, 0.25f, -0.25f,
                -0.25f, 0.25f, 0.25f,
                -0.25f, -0.25f, 0.25f,
                0.25f, -0.25f, 0.25f,
                0.25f, -0.25f, -0.25f,
                0.25f, -0.25f, -0.25f,
                -0.25f, -0.25f, -0.25f,
                -0.25f, -0.25f, 0.25f,
                -0.25f, 0.25f, -0.25f,
                0.25f, 0.25f, -0.25f,
                0.25f, 0.25f, 0.25f,
                0.25f, 0.25f, 0.25f,
                -0.25f, 0.25f, 0.25f,
                -0.25f, 0.25f, -0.25f
            };

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*
            glfwGetWindowSize(window, pWidth, pHeight);// Get the window size passed to glfwCreateWindow
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor()); // Get the resolution of the primary monitor
            glfwSetWindowPos(// Center the window
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically
        
        glfwMakeContextCurrent(window);// Make the OpenGL context current
        glfwSwapInterval(1);        // Enable v-sync

        // Make the window visible
        glfwShowWindow(window);
        GL.createCapabilities();//Critical
        System.out.println("OpenGL Verion: " + glGetString(GL_VERSION));
        this.compileShader();

        mv_location = glGetUniformLocation(program, "mv_matrix");
        proj_location = glGetUniformLocation(program, "proj_matrix");

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        buffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL_ARRAY_BUFFER, buffer);

        GL15.glBufferData(GL_ARRAY_BUFFER,
                vertex_positions,
                GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, NULL);
        glEnableVertexAttribArray(0);

        glEnable(GL_CULL_FACE);
        glFrontFace(GL_CW);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
    }

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop() {

        while (!glfwWindowShouldClose(window)) {
            final float green[] = {0.0f, 0.25f, 0.0f, 1.0f};
            final float one[] = {1.0f};

            IntBuffer w = BufferUtils.createIntBuffer(1);
            IntBuffer h = BufferUtils.createIntBuffer(1);
            glfwGetWindowSize(window, w, h);
            int width = w.get(0);
            int height = h.get(0);
            float aspect = (float) width / (float) height;
            glViewport(0, 0, width, height);
            new Matrix4f()
                    .perspective((float) Math.toRadians(50.0f), aspect, 0.01f, 1000.0f)
                    .lookAt(0.0f, 0.0f, 10.0f,
                            0.0f, 0.0f, 0.0f,
                            0.0f, 1.0f, 0.0f).set(this.proj_matrix);
            
            double curTime = System.currentTimeMillis() / 1000.0;
            float t = (float) curTime * 0.3f;//assigned direcly but I was applying a factor here

            glClearBufferfv(GL_COLOR, 0, green);
            glClearBufferfv(GL_DEPTH, 0, one);

            glUseProgram(program);
            glUniformMatrix4fv(proj_location, false, proj_matrix);

            FloatBuffer mv_matrix = BufferUtils.createFloatBuffer(16);
            new Matrix4f().translate(new Vector3f(0.0f, 0.0f, -4.0f))
                    .translate(
                            new Vector3f(
                                    (float) Math.sin(2.1 * t) * 0.5f,
                                    (float) Math.cos(1.7 * t) * 0.5f,
                                    (float) Math.sin(1.3 * t)
                                    * (float) Math.cos(1.5 * t) * 2.0f)
                    )
                    .rotate((float) Math.toRadians(45.0f) * t, 0.0f, 1.0f, 0.0f)
                    .rotate((float) Math.toRadians(81.0f) * t, 1.0f, 0.0f, 0.0f)
                    .set(mv_matrix);
            
            glUniformMatrix4fv(mv_location, false, mv_matrix);
            glDrawArrays(GL_TRIANGLES, 0, 36);

            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();
        }
        glDeleteVertexArrays(vao);
        glDeleteProgram(program);
    }

    private String readFileAsString(String filename) {
        String next = new Scanner(Ch5SpinningCube.class.getResourceAsStream(filename), "UTF-8").useDelimiter("\\A").next();
        System.out.println("readFileAsString: " + next);
        return next;
    }

    private void compileShader() {
        int vertex_shader = createShader("/vert.glsl", GL_VERTEX_SHADER);
        checkShader(vertex_shader);
        int fragment_shader = createShader("/frag.glsl", GL_FRAGMENT_SHADER);
        checkShader(fragment_shader);
        program = glCreateProgram();
        glAttachShader(program, vertex_shader);
        glAttachShader(program, fragment_shader);
        glLinkProgram(program);
        //check link       
        if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
        //delete shaders as the program has them now
        glDeleteShader(vertex_shader);
        glDeleteShader(fragment_shader);
    }

    public int createShader(final String path, final int shaderType) {
        String shaderSource = readFileAsString(path);
        int shader_id = glCreateShader(shaderType);
        glShaderSource(shader_id, shaderSource);
        glCompileShader(shader_id);
        return shader_id;
    }

    public void checkShader(final int shaderId) {
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(shaderId));
            System.exit(1);
        }
    }
}
