#version 430 core

uniform sampler2D TEX_SAMPLER;
uniform vec4 COLOR_FACTOR;

in vec4 fColor;
in vec2 fTexCoords;

out vec4 color;

void main()
{
    color = texture(TEX_SAMPLER, fTexCoords) + COLOR_FACTOR;
}
