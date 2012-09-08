package mri.v3ds;

/**
 * Light definition.
 */
public class Light3ds 
{
  // Light name
  String mName = "";

  // Light state (on/off)
  boolean mOff = false;

  // Light position
  Vertex3ds mPosition = new Vertex3ds(1.0f, 0.0f, 0.0f);

  // Light target
  Vertex3ds mTarget = new Vertex3ds(0.0f, 0.0f, 0.0f);

  // Light color
  Color3ds mColor = new Color3ds();

  // Light hotspot
  float mHotspot;

  // Light falloff
  float mFalloff;

  // light outer range
  float mOuterRange;

  // light inner range
  float mInnerRange;

  // light multiplexer
  float mMultiplexer;

  // light attenuation
  float mAttenuation;

  // light roll
  float mRoll;

  // light shadowed
  boolean mShadowed;

  // light shadow bias
  float mShadowBias;

  // light shadow filter
  float mShadowFilter;

  // light shadow size
  float mShadowSize;

  // light cone
  boolean mCone;

  // light rectangular
  boolean mRectangular;

  // light aspect
  float mAspect;

  // light projector
  boolean mProjector;

  // light projector name
  String mProjectorName;

  // light overshoot
  boolean mOvershoot;

  // light ray bias
  float mRayBias;

  // light ray shadowes
  boolean mRayShadows;




  /**
   * Get light name.
   *
   * @return light name
   */
  public String name() {
    return mName;
  }

  /**
   * Get light state.
   *
   * @return light state (on/off)
   */
  public boolean off() {
    return mOff;
  }

  /**
   * Get light color.
   *
   * @return light color
   */
  public Color3ds color() {
    return mColor;
  }

  /**
   * Get fixed light position.
   *
   * @return fixed position of light
   */
  public Vertex3ds fixedPosition() {
    return mPosition;
  }

  /**
   * Get fixed light target.
   *
   * @return fixed target of light
   */
  public Vertex3ds fixedTarget() {
    return mTarget;
  }

  /**
   * Get light hotspot.
   *
   * @return light hotspot
   */
  public float hotspot() {
    return mHotspot;
  }

  /**
   * Get light falloff.
   *
   * @return light falloff
   */
  public float falloff() {
    return mFalloff;
  }

  /**
   * Get light outer range.
   *
   * @return light outer range
   */
  public float outerRange() {
    return mOuterRange;
  }

  /**
   * Get light inner range.
   *
   * @return light inner range
   */
  public float innerRange() {
    return mInnerRange;
  }

  /**
   * Get light multiplexer.
   *
   * @return light multiplexer
   */
  public float multiplexer() {
    return mMultiplexer;
  }

  /**
   * Get light attenuation.
   *
   * @return light attenuation
   */
  public float attenuation() {
    return mAttenuation;
  }

  /**
   * Get light roll.
   *
   * @return light roll
   */
  public float roll() {
    return mRoll;
  }

  /**
   * Get light shadowed.
   *
   * @return light shadowed
   */
  public boolean shadowed() {
    return mShadowed;
  }

  /**
   * Get light shadow bias.
   *
   * @return light shadow bias
   */
  public float shadowBias() {
    return mShadowBias;
  }

  /**
   * Get light shadow filter.
   *
   * @return light shadow filter
   */
  public float shadowFilter() {
    return mShadowFilter;
  }

  /**
   * Get light shadow size.
   *
   * @return light shadow size
   */
  public float shadowSize() {
    return mShadowSize;
  }

  /**
   * Get light cone.
   *
   * @return light cone
   */
  public boolean cone() {
    return mCone;
  }

  /**
   * Get light rectangular.
   *
   * @return light rectangular
   */
  public boolean rectangular() {
    return mRectangular;
  }

  /**
   * Get light aspect.
   *
   * @return light aspect
   */
  public float aspect() {
    return mAspect;
  }

  /**
   * Get light projector.
   *
   * @return light projector
   */
  public boolean projector() {
    return mProjector;
  }

  /**
   * Get light projector name.
   *
   * @return light projector name
   */
  public String projectorName() {
    return mProjectorName;
  }

  /**
   * Get light overshoot.
   *
   * @return light overshoot
   */
  public boolean overshoot() {
    return mOvershoot;
  }

  /**
   * Get light ray bias.
   *
   * @return light ray bias
   */
  public float rayBias() {
    return mRayBias;
  }

  /**
   * Get light ray shadows.
   *
   * @return light ray shadows
   */
  public boolean rayShadows() {
    return mRayShadows;
  }

}