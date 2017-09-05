package com.moldedbits.showcase;

import com.moldedbits.showcase.api.MockApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MockApiModule.class})
interface MockApiComponent extends ApiComponent {
}
