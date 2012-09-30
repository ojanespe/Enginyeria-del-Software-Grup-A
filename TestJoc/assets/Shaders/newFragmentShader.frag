varying vec3 vLight;
varying vec4 vDiff;
varying vec4 vView;
varying vec3 vNormal;
varying vec4 vMat;
varying vec2 texCoord0;
 
uniform vec4 vWidths;
uniform sampler2D colorMap0;
uniform float m_Time;
uniform float m_Strength;
 
void main(){
    vec3 vNorm = normalize(vNormal);
    vec4 vColour = vMat;
     
    vec3 vLightN = normalize(vLight);  
    vec3 vViewN = normalize(vView.xyz);
    float ndotv = dot(vNorm, vLightN); //vViewN
    // Random, adding values to get rid of edge errors  and mods that return 0
    float x = (texCoord0.x+4.0) * (texCoord0.y+4.0) * (m_Time*10.0);
    vec4 grain = vec4(mod((mod(x, 13.0) + 1.0) * (mod(x, 123.0) + 1.0), 0.01)-0.005) * m_Strength;
     
    float ndotl = dot(vNorm, vLightN);
    vec4 base0 = texture2D(colorMap0, texCoord0);
    gl_FragColor =  base0 * gl_FrontMaterial.diffuse /* gl_LightSource[0].diffuse*/;
     
    if ( ndotl < vWidths.x )
        vColour *= 0.75+grain;
    if ( ndotl < vWidths.y )
        vColour *= 0.5;
    if ( ndotl < vWidths.z )
        vColour *= 0.5;
    if ( ndotv < vWidths.w ){ 
        vColour *= 0.0;
         
         
    }
    gl_FragColor *= (vColour+base0) * gl_FrontMaterial.diffuse;
}

